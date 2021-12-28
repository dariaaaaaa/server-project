package com.projectmanagementsystem.serverproject.api;

import com.projectmanagementsystem.serverproject.api.dto.UserId;
import com.projectmanagementsystem.serverproject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public final class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<com.projectmanagementsystem.serverproject.repo.model.Project>> index(@RequestBody UserId userId) {
        final List<com.projectmanagementsystem.serverproject.repo.model.Project> projects = projectService.fetchAllByOwnerId(userId.getId());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.projectmanagementsystem.serverproject.repo.model.Project> show(@PathVariable long id) {
        try {
            final com.projectmanagementsystem.serverproject.repo.model.Project project = projectService.fetchById(id);
            return ResponseEntity.ok(project);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.projectmanagementsystem.serverproject.api.dto.Project project) {
        final String title = project.getTitle();
        final String description = project.getDescription();
        final Date dateStart = project.getDateStart();
        final Date dateEnd = project.getDateEnd();
        final long ownerId = project.getOwnerId();

        try {
            final long id = projectService.create(title, description, dateStart, dateEnd, ownerId);
            final String location = String.format("/projects/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.projectmanagementsystem.serverproject.api.dto.Project project) {
        final String title = project.getTitle();
        final String description = project.getDescription();
        final Date dateStart = project.getDateStart();
        final Date dateEnd = project.getDateEnd();
        final long ownerId = project.getOwnerId();
        try {
            projectService.update(id, title, description, dateStart, dateEnd, ownerId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path="/exist", method = RequestMethod.HEAD)
    public ResponseEntity<Void> exist(@RequestParam long id) {
        try{
            projectService.ifExistById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}