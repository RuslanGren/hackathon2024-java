package com.ua.hackathon2024java.web.controllers;

import com.ua.hackathon2024java.DTOs.user.JwtRequestDto;
import com.ua.hackathon2024java.DTOs.user.JwtResponseDto;
import com.ua.hackathon2024java.controllers.AuthController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthPageController {
    private final AuthController authController;

    @Value("${jwt.secret}")
    private String secretKey;

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

            // Декодуємо токен, щоб отримати інформацію про роль користувача
            String token = jwtResponseDto.getToken();
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)  // Замість 'your-secret-key' використовуйте свій секретний ключ
                    .parseClaimsJws(token)
                    .getBody();

            // Отримуємо ролі з токену
            List<String> roles = claims.get("roles", List.class);

            // Якщо користувач має роль ADMIN, перенаправляємо на /admin/dashboard
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/reports"; // Якщо користувач не має ролі ADMIN, перенаправляємо на /reports
            }

        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password");
            return "login"; // Помилка логіну
        }
    }
}