package com.ua.hackathon2024java.services;


import com.ua.hackathon2024java.DTOs.user.JwtRequestDto;
import com.ua.hackathon2024java.DTOs.user.JwtResponseDto;

public interface AuthService {
    JwtResponseDto createAuthToken(JwtRequestDto jwtRequestDto);
    // UserResponseDto registerNewUser(UserRequestDto userRequestDto);
}