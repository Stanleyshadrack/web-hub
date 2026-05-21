package com.web_hub.web_hub.hr.leave.api.dto;

import java.time.LocalDate;
public record LeaveResponse(
        Long id,
        String type,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        String reason   // add if needed
) {}
