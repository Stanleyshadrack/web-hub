package com.web_hub.web_hub.admin.dto;

import com.web_hub.web_hub.role.Role;

public record CreateUserRequest(
        String email,
        String username,
        Role role
) {}
