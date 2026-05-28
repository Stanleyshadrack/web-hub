package com.web_hub.web_hub.admin;

import com.web_hub.web_hub.role.Role;

public record UpdateUserRequest(
        String username,
        String email,
        Role role,
        Boolean active,
        String firstName,
        String lastName,
        String jobTitle,
        String phoneNumber,
        String department,
        String location
) {}
