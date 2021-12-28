package com.projectmanagementsystem.serverproject.repo.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="projects")
public final class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Date dateStart;
    private Date dateEnd;
    @NotNull
    private long ownerId;

    public Project() {
    }

    public Project(String title, String description, Date dateStart, Date dateEnd, long ownerId) {
        this.title = title;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.ownerId = ownerId;
    }

    public long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
