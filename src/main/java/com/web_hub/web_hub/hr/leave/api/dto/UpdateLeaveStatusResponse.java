package com.web_hub.web_hub.hr.leave.api.dto;


import com.web_hub.web_hub.hr.leave.model.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLeaveStatusResponse {

    private Long id;
    private LeaveStatus status;
    private Long approverId;
    private LocalDateTime decisionDate;
    private String message;
}
