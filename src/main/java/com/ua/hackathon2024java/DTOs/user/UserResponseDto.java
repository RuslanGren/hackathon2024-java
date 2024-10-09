package com.ua.hackathon2024java.DTOs.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;

    private String email;

    private String username;

    private String description;

    private long number;

    @JsonProperty("created_at")
    private Instant createdAt;
}