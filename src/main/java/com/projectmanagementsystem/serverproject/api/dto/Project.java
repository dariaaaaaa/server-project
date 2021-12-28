package com.projectmanagementsystem.serverproject.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public final class Project {
    private String title;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private Long ownerId;
}
