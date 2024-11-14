package com.ua.hackathon2024java.DTOs.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    private String firstName;

    private String lastName;

    private String fatherName;

    private String number;

    private String region;

    private String category;

    private String address;

    private String text;

    private String additionalInformation;
}