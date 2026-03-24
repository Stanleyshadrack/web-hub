package com.web_hub.web_hub.admin.dto;

public record AssignRoleRequest(
        Long userId,
        String role
) {}
