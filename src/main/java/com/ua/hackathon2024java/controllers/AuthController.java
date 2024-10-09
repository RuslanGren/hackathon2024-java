package com.ua.hackathon2024java.controllers;

import com.ua.hackathon2024java.DTOs.user.JwtRequestDto;
import com.ua.hackathon2024java.DTOs.user.JwtResponseDto;
import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody @Valid JwtRequestDto jwtRequestDto) {
        return authService.createAuthToken(jwtRequestDto);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto userRequestDto) {
        return authService.registerNewUser(userRequestDto);
    }
}