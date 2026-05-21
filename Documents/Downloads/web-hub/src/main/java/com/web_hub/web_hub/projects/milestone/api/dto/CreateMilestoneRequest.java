package com.web_hub.web_hub.projects.milestone.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMilestoneRequest {

    private Long projectId;
    private String name;
    private String status;
    private LocalDate dueDate;
}
