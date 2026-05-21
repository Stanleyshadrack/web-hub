package com.web_hub.web_hub.admin.auditlog.service;

import com.web_hub.web_hub.admin.auditlog.model.AuditLog;
import com.web_hub.web_hub.admin.auditlog.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;


    public void logAction(String action,
                          String entity,
                          Long entityId,
                          String performedBy,
                          String role,
                          String details) {

        AuditLog log = AuditLog.builder()
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .performedBy(performedBy)
                .role(role)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }


    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
