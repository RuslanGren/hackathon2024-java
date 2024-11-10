package com.ua.hackathon2024java.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ReportMakingController {

    @GetMapping("/create-complaint")
    public String showCreateComplaintForm(Model model) {
        return "create-complaint";
    }

    @PostMapping("/submit-complaint")
    public String submitComplaint(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String region,
                                  @RequestParam(required = false) String city,
                                  @RequestParam(required = false) String phone,
                                  @RequestParam String description,
                                  @RequestParam String category,
                                  @RequestParam List<MultipartFile> media,
                                  @RequestParam(required = false) String contactInfo,
                                  Model model) {
        // Process the submission
        if (firstName != null && lastName != null && region != null && city != null && phone != null) {
            // Detailed complaint
            // Save the complaint with user details to the database
        } else {
            // Anonymous complaint
            // Save the complaint as anonymous and optionally save contactInfo
        }

        // Save media files
        if (!media.isEmpty()) {
            for (MultipartFile file : media) {
                try {
                    String uploadDir = "/path/to/save/uploads/";
                    File uploadFile = new File(uploadDir + file.getOriginalFilename());
                    file.transferTo(uploadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("message", "Не вдалося завантажити файл(и). Будь ласка, спробуйте ще раз.");
                    return "create-complaint";
                }
            }
        }

        // Return to the main page with a success message
        model.addAttribute("message", "Заявку подано успішно!");
        return "redirect:/create-complaint";
    }
}
