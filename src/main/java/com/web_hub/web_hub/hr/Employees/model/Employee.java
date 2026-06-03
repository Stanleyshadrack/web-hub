package com.web_hub.web_hub.hr.Employees.model;
import com.web_hub.web_hub.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentId;
    private String firstName;
    private String lastName;
    private String email;

    private String position;
    private String department;
    private Double salary;
    private String status; // ACTIVE
    private String phone;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
