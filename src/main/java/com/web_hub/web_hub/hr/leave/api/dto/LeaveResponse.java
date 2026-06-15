package com.web_hub.web_hub.hr.leave.api.dto;

import com.web_hub.web_hub.hr.leave.model.LeaveStatus;
import com.web_hub.web_hub.hr.leave.model.LeaveType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeaveResponse(
        Long id,
        String referenceId,
        String employeeName,
        LeaveType type,
        LocalDate startDate,
        LocalDate endDate,
        Integer numberOfDays,
        LeaveStatus status,
        String reason,
        String approverName,
        LocalDateTime decisionDate,
        LocalDateTime createdAt
) {}
