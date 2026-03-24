package com.web_hub.web_hub.admin.dto;

public record AssetRequest(
        String name,
        String type,
        Long assignedUserId
) {}
