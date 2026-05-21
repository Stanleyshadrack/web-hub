package com.web_hub.web_hub.projects.milestone.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MilestoneResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String status;
    private LocalDate dueDate;
}
