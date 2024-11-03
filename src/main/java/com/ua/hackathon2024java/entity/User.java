package com.ua.hackathon2024java.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    private String description;

    private long number;

    @Builder.Default
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();
}