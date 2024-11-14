package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.ReportCategory;
import com.ua.hackathon2024java.entity.ReportStatus;
import com.ua.hackathon2024java.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportPageController {
    private final ReportService reportService;

    @GetMapping("/reports")
    public String getReports(
            @RequestParam(value = "status", required = false) ReportStatus status,
            @RequestParam(value = "category", required = false) ReportCategory category,
            @RequestParam(value = "createdAfter", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdAfter,
            @RequestParam(value = "createdBefore", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdBefore,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            Model model) {

        List<ReportResponseDto> reports = reportService.filterAndSortReports(
                status, category, createdAfter, createdBefore, sortBy, direction);

        model.addAttribute("reports", reports);

        return "reports";
    }
}
