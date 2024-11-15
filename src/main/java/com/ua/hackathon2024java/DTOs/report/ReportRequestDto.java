package com.ua.hackathon2024java.DTOs.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("father_name")
    private String fatherName;

    private String number;

    private String region;

    private String category;

    private String address;

    private String text;

    @JsonProperty("additional_information")
    private String additionalInformation;

    private List<MultipartFile> files = new ArrayList<>();
}