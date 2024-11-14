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

    private String status;

    private String firstName;

    private String lastName;

    private String fatherName;

    private String number;

    private String region;

    private String category;

    private String address;

    private String text;

    private String additionalInformation;

    private String url;

    @JsonProperty("created_at")
    private Instant createdAt;
}