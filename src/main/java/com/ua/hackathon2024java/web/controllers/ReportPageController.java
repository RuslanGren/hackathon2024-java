package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.DTOs.report.ReportRequestDto;
import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.entity.Regions;
import com.ua.hackathon2024java.entity.ReportCategory;
import com.ua.hackathon2024java.entity.ReportStatus;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportPageController {
    private final ReportService reportService;


    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/create-report")
    public String showCreateReportPage(Model model) {
        model.addAttribute("report", new ReportRequestDto());
        return "create-report";
    }

    @PostMapping("/create")
    public String createReportSubmit(@ModelAttribute ReportRequestDto report) {
        if (!Regions.exists(report.getRegion())) {
            throw new BadRequestException("Invalid region: " + report.getRegion());
        }

        reportService.createReport(report);
        return "redirect:/success";
    }

    @GetMapping("/reports")
    public String getReports(
            @RequestParam(value = "status", required = false) ReportStatus status,
            @RequestParam(value = "category", required = false) ReportCategory category,
            @RequestParam(value = "createdAfter", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdAfter,
            @RequestParam(value = "createdBefore", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdBefore,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            Model model) {

        List<ReportResponseDto> reports = reportService.filterAndSortReportsResponseDto(
                status, category, createdAfter, createdBefore, sortBy, direction);

        model.addAttribute("reportIds", new ArrayList<Long>()); // Pass an empty list or selected IDs
        model.addAttribute("reports", reports);

        return "reports";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam Long reportId, @RequestParam String status) {
        reportService.changeStatus(reportId, status);
        return "redirect:/reports";
    }
}
