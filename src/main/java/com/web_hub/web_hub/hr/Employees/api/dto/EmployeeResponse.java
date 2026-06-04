package com.web_hub.web_hub.hr.Employees.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String departmentId;
    private String jobTitle;
    private String status;
    
    // Core HR onboarding fields
    private String startDate;
    private String identificationNumber;
    private String idType;
    private String nationality;

    // Financial/Tax fields
    private String bankName;
    private String accountNumber;
    private String kraPin;

    private LocalDateTime createdAt;
}