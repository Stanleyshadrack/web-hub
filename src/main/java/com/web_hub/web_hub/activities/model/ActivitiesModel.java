package com.web_hub.web_hub.activities.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "activities") // Corrected spelling from 'activies'
@Data
public class ActivitiesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String actionType;

    private Long targetId;

    private String targetType;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String userAgent;
}
