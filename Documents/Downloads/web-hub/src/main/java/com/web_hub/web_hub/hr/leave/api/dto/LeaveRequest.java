package com.web_hub.web_hub.hr.leave.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequest {

    private Long id;

    private Long employeeId;
    private String employeeName;

    private String type; // ANNUAL, SICK, etc

    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;

    private String status; // PENDING, APPROVED, REJECTED
}
