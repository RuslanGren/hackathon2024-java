package com.ua.hackathon2024java.DTOs.user;

import lombok.*;

@Getter
@Setter
public class JwtResponseDto extends UserResponseDto {
    private String token;

    public JwtResponseDto(UserResponseDto user, String token) {
        super(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getDescription(),
                user.getNumber(),
                user.getCreatedAt()
        );
        this.token = token;
    }
}