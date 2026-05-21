package com.web_hub.web_hub.admin.auditlog.repository;

import com.web_hub.web_hub.admin.auditlog.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
