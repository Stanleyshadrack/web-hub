package com.web_hub.web_hub.projects.project.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeProjectResponse {

    private Long projectId;
    private String projectName;
    private String status;
}
