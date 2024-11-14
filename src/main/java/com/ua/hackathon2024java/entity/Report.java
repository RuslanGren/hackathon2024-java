package com.ua.hackathon2024java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String number;

    @Enumerated(EnumType.STRING)
    private Regions region;

    @Enumerated(EnumType.STRING)
    private ReportCategory category;

    private String address;

    private String text;

    private String additionalInformation;

    private String url;

    @Builder.Default
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
