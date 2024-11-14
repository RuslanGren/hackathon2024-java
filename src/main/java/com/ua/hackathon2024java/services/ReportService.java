package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.ReportCategory;
import com.ua.hackathon2024java.entity.ReportStatus;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;

public interface ReportService {
    ReportResponseDto createReport(ReportRequestDto reportRequestDto);
    ReportResponseDto getReportResponseById(Long id);
    byte[] downloadPdf(Iterable<Long> reportIds);
    List<ReportResponseDto> filterAndSortReports(
            ReportStatus status,
            ReportCategory category,
            Instant createdAfter,
            Instant createdBefore,
            String sortBy,
            Sort.Direction direction);
}
