package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.services.ReportService;
import com.ua.hackathon2024java.web.temporary.ReportResponseDtoTemp;
import com.ua.hackathon2024java.web.temporary.ReportServiceTemp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportPageController {

    private final ReportServiceTemp reportServiceTemp;

    public ReportPageController(ReportServiceTemp reportServiceTemp) {
        this.reportServiceTemp = reportServiceTemp;
    }

    @GetMapping("/report")
    public String homePage(@RequestParam(required = false) String sortBy,
                           @RequestParam(required = false) String filterCategory,
                           @RequestParam(required = false) String filterStatus,
                           @RequestParam(required = false) String filterRegion,
                           Model model) {
        List<ReportResponseDtoTemp> reports = reportServiceTemp.findAllSortedAndFiltered(sortBy, filterCategory, filterStatus, filterRegion);

        model.addAttribute("reports", reports);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("filterCategory", filterCategory);
        model.addAttribute("filterStatus", filterStatus);
        model.addAttribute("filterRegion", filterRegion);

        if (reports.isEmpty()) {
            model.addAttribute("message", "Немає доступних заявок.");
        }

        return "report"; // Назва шаблону для домашньої сторінки
    }
}
