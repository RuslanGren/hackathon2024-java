package com.ua.hackathon2024java.controllers;

import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

}