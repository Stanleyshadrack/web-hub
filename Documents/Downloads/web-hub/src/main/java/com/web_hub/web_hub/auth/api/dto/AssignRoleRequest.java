package com.web_hub.web_hub.auth.api.dto;

public record AssignRoleRequest(
        Long userId,
        String role
) {}
