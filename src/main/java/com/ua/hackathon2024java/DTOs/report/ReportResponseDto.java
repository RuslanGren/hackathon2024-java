package com.ua.hackathon2024java.DTOs.report;

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
public class ReportResponseDto {
    private Long id;

    private String name;

    private String city;

    private String number;

    private String text;

    @JsonProperty("created_at")
    private Instant createdAt;
}