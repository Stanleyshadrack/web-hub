package com.web_hub.web_hub.admin.dto;

public record AssetResponse(
        Long id,
        String name,
        String type,
        Long assignedUserId
) {}
