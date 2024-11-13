package com.ua.hackathon2024java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    private String status;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String number;

    @Enumerated(EnumType.STRING)
    private Regions region;

    private String address;

    private String text;

    private String url;

    @Builder.Default
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
