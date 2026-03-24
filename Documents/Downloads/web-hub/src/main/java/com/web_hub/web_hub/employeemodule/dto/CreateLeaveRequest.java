package com.web_hub.web_hub.employeemodule.dto;

import java.time.LocalDate;

public record CreateLeaveRequest(
        String type,
        LocalDate startDate,
        LocalDate endDate,
        String reason
) {}
