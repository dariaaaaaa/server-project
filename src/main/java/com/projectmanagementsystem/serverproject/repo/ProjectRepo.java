package com.projectmanagementsystem.serverproject.repo;

import com.projectmanagementsystem.serverproject.repo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    @Query
    List<Project> findAllByOwnerId(long ownerId);
}