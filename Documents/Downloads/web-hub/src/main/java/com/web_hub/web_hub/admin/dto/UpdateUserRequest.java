package com.web_hub.web_hub.admin.dto;

import com.web_hub.web_hub.role.Role;

public record UpdateUserRequest(
        String username,
        Role role,
        Boolean active
) {}
