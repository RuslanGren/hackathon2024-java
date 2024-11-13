package com.ua.hackathon2024java.factories;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportDtoFactory {

    public ReportResponseDto makeReportDtoResponse(Report report) {
        return ReportResponseDto.builder()
                .id(report.getId())
                .status(report.getStatus())
                .firstName(report.getFirstName())
                .lastName(report.getLastName())
                .fatherName(report.getFatherName())
                .number(report.getNumber())
                .region(report.getRegion().name())
                .address(report.getAddress())
                .text(report.getText())
                .url(report.getUrl())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
