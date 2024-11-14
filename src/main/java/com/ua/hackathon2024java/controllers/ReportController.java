package com.ua.hackathon2024java.controllers;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ReportResponseDto findReportById(@PathVariable Long id) {
        return reportService.getReportResponseById(id);
    }

    @GetMapping
    public List<ReportResponseDto> findAll() {
        return reportService.findAll();
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam List<Long> reportIds) {
        byte[] pdfData = reportService.downloadPdf(reportIds);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reports.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfData);
    }
}
