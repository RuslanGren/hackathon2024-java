package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportPageController {

    private final ReportService reportService;

    public ReportPageController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public String homePage(@RequestParam(required = false) String sortBy, Model model) {
//        List<ReportResponseDto> reports = reportService.findAllSorted(sortBy);
//
//        model.addAttribute("reports", reports);
//        model.addAttribute("sortBy", sortBy);
//
//        if (reports.isEmpty()) {
//            model.addAttribute("message", "Немає доступних заявок.");
//        }

        return "report"; // Назва шаблону для домашньої сторінки
    }
}
