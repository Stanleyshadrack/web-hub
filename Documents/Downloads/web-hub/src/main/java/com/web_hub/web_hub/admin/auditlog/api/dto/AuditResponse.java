package com.web_hub.web_hub.admin.auditlog.api.dto;

public record AuditResponse(
        Long id,
        String action,
        String performedBy,
        String timestamp
) {}
