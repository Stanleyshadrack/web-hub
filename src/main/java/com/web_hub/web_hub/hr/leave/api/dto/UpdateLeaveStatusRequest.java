package com.web_hub.web_hub.hr.leave.api.dto;

import com.web_hub.web_hub.hr.leave.model.LeaveStatus;
import lombok.Data;

@Data
public class UpdateLeaveStatusRequest {

    private LeaveStatus status;
}
