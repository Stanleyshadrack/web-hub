package com.web_hub.web_hub.admin.assetmanagement.api.dto;

public record AssetResponse(
        Long id,
        String name,
        String type,
        String serialNumber,
        String status,
        String employeeName,
        String employeeEmail
) {}
