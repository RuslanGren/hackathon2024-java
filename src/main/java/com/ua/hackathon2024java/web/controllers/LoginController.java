package com.ua.hackathon2024java.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Повертаємо шаблон login.html
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

//        // Додайте тут логіку аутентифікації користувача
//        if (email.equals("test@example.com") && password.equals("password")) {
//            model.addAttribute("message", "Login successful!");
//            return "welcome"; // перенаправляємо на сторінку привітання після успішного входу
//        } else {
//            model.addAttribute("message", "Invalid email or password");
//            return "login"; // повертаємо на сторінку входу з повідомленням про помилку
//        }
    }
}
