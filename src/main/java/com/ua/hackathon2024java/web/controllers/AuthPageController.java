package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.DTOs.user.JwtRequestDto;
import com.ua.hackathon2024java.DTOs.user.JwtResponseDto;
import com.ua.hackathon2024java.controllers.AuthController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthPageController {
    private final AuthController authController;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletResponse response, Model model) {
        try {
            JwtRequestDto jwtRequestDto = new JwtRequestDto(email, password);
            JwtResponseDto jwtResponseDto = authController.login(jwtRequestDto);

            // Додаємо токен в cookie
            Cookie jwtCookie = new Cookie("JWT", jwtResponseDto.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60); // Термін дії 1 година
            response.addCookie(jwtCookie);

            return "redirect:/reports"; // Успішний логін
        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Помилка логіну
        }
    }
}