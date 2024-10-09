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

    private String name;

    private String city;

    private String number;

    private String text;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "files_urls", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "files_urls")
    private List<String> filesUrls = new ArrayList<>();

    @Builder.Default
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}
