package com.web_hub.web_hub.departments.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {
    private String id;
    private String name;
    private String description;
    private String headCount;
    private String annualBudget;
}
