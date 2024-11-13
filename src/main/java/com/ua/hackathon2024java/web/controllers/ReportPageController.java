package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.DTOs.report.ReportResponseDto;
import com.ua.hackathon2024java.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportPageController {
    private final ReportService reportService;

    @GetMapping("/reports")
    public String getReports(Model model) {
        List<ReportResponseDto> complaints = reportService.getReportsForLoggedUser();
        System.out.println(complaints.getFirst().getId());
        model.addAttribute("complaints", complaints);
        return "reports";
    }
}
