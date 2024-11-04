package com.ua.hackathon2024java.factories;

import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.entity.Role;
import com.ua.hackathon2024java.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoFactory {

    public UserResponseDto makeUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .description(user.getDescription())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .number(user.getNumber())
                .createdAt(user.getCreatedAt())
                .build();
    }
}