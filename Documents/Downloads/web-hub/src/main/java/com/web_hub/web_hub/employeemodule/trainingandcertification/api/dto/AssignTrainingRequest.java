package com.web_hub.web_hub.employeemodule.trainingandcertification.api.dto;

import lombok.Data;

@Data
public class AssignTrainingRequest {
    private Long employeeId;
    private Long trainingId;
}
