package com.web_hub.web_hub.departments.api.dto;

import lombok.Data;

@Data
public class DepartmentRequest {
    private String name;
    private String description;
    private String headOfDepartment;

}
