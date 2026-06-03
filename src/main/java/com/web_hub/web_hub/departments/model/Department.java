package com.web_hub.web_hub.departments.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    private String id;
    private String name;
    private String description;
    private String headOfDepartment;
    private LocalDateTime createdAt;
}
