package com.ua.hackathon2024java.factories;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportDtoFactory {

    public ReportResponseDto makeReportDtoResponse(Report report) {
        return ReportResponseDto.builder()
                .id(report.getId())
                .name(report.getName())
                .city(report.getCity())
                .number(report.getNumber())
                .text(report.getText())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
