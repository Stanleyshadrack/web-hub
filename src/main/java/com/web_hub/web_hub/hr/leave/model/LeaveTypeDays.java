package com.web_hub.web_hub.hr.leave.model;


public record LeaveTypeDays(
        LeaveType leaveType,
        Long approvedDays,
        Long pendingDays
) {}
