package com.web_hub.web_hub.hr.leave.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "leaves")
public class LeaveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private LeaveType type; // Updated to Enum for better type safety

    @Column(name = "start_date")
    private LocalDate startDate; // Maps to "From"

    @Column(name = "end_date")
    private LocalDate endDate; // Maps to "To"

    @Column(name = "number_of_days")
    private Integer numberOfDays; // NEW: Maps to the "Days" column

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING; // Default to Pending

    @Column(columnDefinition = "TEXT")
    private String reason; // Maps to the text area in the request modal

    // NEW: Audit fields for the "Decision" column
    @Column(name = "approver_id")
    private Long approverId;

    @Column(name = "decision_date")
    private LocalDateTime decisionDate;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}