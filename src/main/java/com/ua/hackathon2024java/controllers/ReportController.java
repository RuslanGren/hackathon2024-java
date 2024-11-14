package com.ua.hackathon2024java.controllers;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.ReportCategory;
import com.ua.hackathon2024java.entity.ReportStatus;
import com.ua.hackathon2024java.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/create")
    public ReportResponseDto createReport(@RequestBody ReportRequestDto reportRequestDto) {
        return reportService.createReport(reportRequestDto);
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam List<Long> reportIds) {
        byte[] pdfData = reportService.downloadPdf(reportIds);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reports.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam Long reportId, @RequestParam String status) {
        reportService.changeStatus(reportId, status);
        return "redirect:/reports";
    }

    @GetMapping
    public List<ReportResponseDto> filterAndSortReports(
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(required = false) ReportCategory category,
            @RequestParam(required = false) Instant createdAfter,
            @RequestParam(required = false) Instant createdBefore,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        return reportService.filterAndSortReportsResponseDto(status, category, createdAfter, createdBefore, sortBy, direction);
    }
}
