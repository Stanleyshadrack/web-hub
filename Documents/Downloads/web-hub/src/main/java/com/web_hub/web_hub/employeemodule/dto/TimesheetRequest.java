package com.web_hub.web_hub.employeemodule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRequest {
    private String projectName;
    private String date;
    private double hoursWorked;
}
