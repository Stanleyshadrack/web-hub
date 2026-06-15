package com.web_hub.web_hub.hr.leave.api.dto;


import com.web_hub.web_hub.hr.leave.model.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveTrackerResponse {
    private LeaveType leaveType;
    private String displayName;
    private int totalAllocated;
    private int daysUsed;
    private int daysRemaining;
}
