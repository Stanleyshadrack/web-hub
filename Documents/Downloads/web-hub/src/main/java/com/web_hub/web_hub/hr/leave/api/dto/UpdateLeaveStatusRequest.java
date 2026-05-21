package com.web_hub.web_hub.hr.leave.api.dto;

import lombok.Data;

@Data
public class UpdateLeaveStatusRequest {

    private String status; // APPROVED / REJECTED / ESCALATED
}
