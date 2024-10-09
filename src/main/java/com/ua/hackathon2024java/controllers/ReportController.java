package com.ua.hackathon2024java.controllers;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.services.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/create")
    public ReportResponseDto createReport(@RequestBody @Valid ReportRequestDto reportRequestDto) {
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
}
