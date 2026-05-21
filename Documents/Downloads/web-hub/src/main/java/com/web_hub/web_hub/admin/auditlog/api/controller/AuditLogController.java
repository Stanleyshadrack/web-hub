package com.web_hub.web_hub.admin.auditlog.api.controller;

import com.web_hub.web_hub.admin.auditlog.model.AuditLog;
import com.web_hub.web_hub.admin.auditlog.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogService.getAllLogs();
    }
}
