package com.projectmanagementsystem.serverproject.service;

import com.projectmanagementsystem.serverproject.repo.ProjectRepo;
import com.projectmanagementsystem.serverproject.repo.model.Project;
import com.projectmanagementsystem.serverproject.service.requests.TeamRequests;
import com.projectmanagementsystem.serverproject.service.requests.UserRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

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

    @Transactional
    public long create(String title, String description, Date dateStart, Date dateEnd, long ownerId) throws IllegalArgumentException{

        //UserRequests.checkForExisting(ownerId);

        final Project project = new Project(title, description, dateStart, dateEnd, ownerId);
        final Project savedProject = projectRepo.save(project);

        TeamRequests.insertOwner(ownerId, savedProject.getId());

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
