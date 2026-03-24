package com.web_hub.web_hub.employeemodule.dto;

public record PayslipResponse(
        Long id,
        double salary,
        double deductions,
        double netPay
) {}
