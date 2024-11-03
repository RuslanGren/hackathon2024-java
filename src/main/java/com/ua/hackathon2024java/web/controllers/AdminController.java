package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.web.temporary.Inspector;
import com.ua.hackathon2024java.web.temporary.InspectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InspectorService inspectorService;

    public AdminController(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<String> regions = List.of("Kyiv", "Lviv", "Odesa"); // Available regions
        List<Inspector> inspectors = inspectorService.getInspectors();

        model.addAttribute("regions", regions);
        model.addAttribute("inspectors", inspectors);
        return "admin";  // Refers to admin.html
    }

    @PostMapping("/inspectors")
    public String createInspector(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam List<String> regions) {
        inspectorService.createInspector(email, password, regions);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/inspectors/{id}")
    public String editInspectorRegions(@PathVariable Long id, @RequestParam List<String> regions) {
        inspectorService.updateInspectorRegions(id, regions);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/inspectors/delete/{id}")
    public String deleteInspector(@PathVariable Long id) {
        inspectorService.deleteInspector(id);
        return "redirect:/admin/dashboard";
    }
}
