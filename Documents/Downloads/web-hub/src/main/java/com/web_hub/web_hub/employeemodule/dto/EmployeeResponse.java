package com.web_hub.web_hub.employeemodule.dto;

import com.web_hub.web_hub.role.Role;

public record EmployeeResponse(
        Long id,
        String email,
        String username,
        Role role,
        boolean active
) {}
