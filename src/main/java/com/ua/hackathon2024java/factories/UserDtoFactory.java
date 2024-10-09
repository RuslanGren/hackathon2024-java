package com.ua.hackathon2024java.factories;

import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoFactory {

    public UserResponseDto makeUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .description(user.getDescription())
                .number(user.getNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}