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
    public String submitComplaint(@RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("region") String region,
                                  @RequestParam("city") String city,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("subject") String subject,
                                  @RequestParam("description") String description,
                                  @RequestParam("category") String category,
                                  @RequestParam("media") List<MultipartFile> media,
                                  Model model) {
        // Обробка подання заявки
        // Збереження заявки у базу даних або інші дії

        // Збереження медіафайлів
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

        // Повернення до головної сторінки з повідомленням про успішне подання
        model.addAttribute("message", "Ваша заявка успішно подана!");
        return "redirect:/";
    }
}
