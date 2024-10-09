package com.ua.hackathon2024java.DTOs.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 5, max = 30, message = "Username should be between 5 and 30 characters")
    private String name;

    @NotBlank(message = "City should not be empty!")
    @Size(min = 5, max = 30, message = "Email should be between 5 and 30 characters")
    private String city;

    @NotBlank(message = "Number should not be empty!")
    @Size(min = 4, max = 30, message = "Number should be between 4 and 30 characters")
    private String number;

    @NotBlank(message = "Text should not be empty!")
    private String text;
}