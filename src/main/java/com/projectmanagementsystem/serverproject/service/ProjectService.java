package com.projectmanagementsystem.serverproject.service;

import com.projectmanagementsystem.serverproject.repo.ProjectRepo;
import com.projectmanagementsystem.serverproject.repo.model.Project;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ProjectService {

    private final ProjectRepo projectRepo;


    private final String userBaseUrl = "http://server-user:8081/users";
    private final String teamBaseUrl = "http://server-team:8082/team";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Project> fetchAll() {
        return projectRepo.findAll();
    }

    public Project fetchById(long id) throws IllegalArgumentException {
        final Optional<Project> maybeProject = projectRepo.findById(id);
        if (maybeProject.isEmpty()) throw new IllegalArgumentException("Project not found");
        else return maybeProject.get();
    }

    public void ifExistById(long id) throws IllegalArgumentException{
        final Optional<Project> maybeProject = projectRepo.findById(id);
        if (maybeProject.isEmpty()) throw new IllegalArgumentException("Project not found");
    }

    public List<Project> fetchAllByOwnerId(long ownerId) {
        return projectRepo.findAllByOwnerId(ownerId);
    }

    public long create(String title, String description, Date dateStart, Date dateEnd, long ownerId) throws IllegalArgumentException{
        final String userUrl = String.format( userBaseUrl + "/exist?id=%d",ownerId);

        try {
        final int userResponse = restTemplate.exchange(URI.create(userUrl), HttpMethod.HEAD, null, Void.class ).getStatusCodeValue();
        } catch (HttpClientErrorException e) { throw new IllegalArgumentException("User not found");}

        final Project project = new Project(title, description, dateStart, dateEnd, ownerId);
        final Project savedProject = projectRepo.save(project);

        final JSONObject teamRequest = new JSONObject();
        teamRequest.put("userId", ownerId);
        teamRequest.put("userRole", 1);
        teamRequest.put("projectId", savedProject.getId());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> request = new HttpEntity<String>(teamRequest.toString(), headers);
        restTemplate.exchange(teamBaseUrl, HttpMethod.POST, request, Void.class );

        return savedProject.getId();
    }

    public void update(long id, String title, String description, Date dateStart, Date dateEnd, long ownerId) throws IllegalArgumentException{
        final Optional<Project> maybeProject = projectRepo.findById(id);
        if (maybeProject.isEmpty()) throw new IllegalArgumentException("Project not found");

        final Project project = maybeProject.get();
        if (title != null && !title.isBlank()) project.setTitle(title);
        if (description != null && !description.isBlank()) project.setDescription(description);
        if (dateStart != null) project.setDateStart(dateStart);
        if (dateEnd != null) project.setDateEnd(dateEnd);
        if (ownerId != 0) project.setOwnerId(ownerId);
        projectRepo.save(project);
    }

    public void delete(long id) {
        projectRepo.deleteById(id);
    }
}
