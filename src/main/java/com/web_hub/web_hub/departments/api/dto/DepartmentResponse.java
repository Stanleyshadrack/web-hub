package com.web_hub.web_hub.departments.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponse {
    private String id;
    private String name;
    private String description;
    private String headCount;
    private String annualBudget;
}
