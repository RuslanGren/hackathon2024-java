package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;

import java.util.List;

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto reportRequestDto);
    ReportResponseDto getReportResponseById(Long id);
    List<ReportResponseDto> findAll();
    List<ReportResponseDto> findAllSorted(String sortBy);
}
