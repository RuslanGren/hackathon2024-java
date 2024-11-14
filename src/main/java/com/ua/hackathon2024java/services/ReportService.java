package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto reportRequestDto);
    ReportResponseDto getReportResponseById(Long id);
    List<ReportResponseDto> findAll();
    List<ReportResponseDto> getReportsForLoggedUser();
    byte[] downloadPdf(Iterable<Long> reportIds);
}
