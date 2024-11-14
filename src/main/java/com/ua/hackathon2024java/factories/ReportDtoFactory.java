package com.ua.hackathon2024java.factories;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportDtoFactory {

    public ReportResponseDto makeReportDtoResponse(Report report) {
        return ReportResponseDto.builder()
                .id(report.getId())
                .status(report.getStatus().name())
                .firstName(report.getFirstName())
                .lastName(report.getLastName())
                .fatherName(report.getFatherName())
                .number(report.getNumber())
                .region(report.getRegion().name())
                .category(report.getCategory().name())
                .address(report.getAddress())
                .text(report.getText())
                .url(report.getUrl())
                .createdAt(report.getCreatedAt())
                .additionalInformation(report.getAdditionalInformation())
                .build();
    }
}
