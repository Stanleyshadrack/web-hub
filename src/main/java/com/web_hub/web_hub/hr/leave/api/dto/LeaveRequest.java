package com.web_hub.web_hub.hr.leave.api.dto;

import com.web_hub.web_hub.hr.leave.model.LeaveStatus;
import com.web_hub.web_hub.hr.leave.model.LeaveType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveRequest {

    private Long id;

    private Long employeeId;
    private String employeeName;

    // Updated from String to Enum
    private LeaveType type;

    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;

    // Updated from String to Enum
    private LeaveStatus status;
}