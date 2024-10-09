package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.entity.User;

import java.util.Optional;

public interface UserService {
    UserResponseDto createNewUser(UserRequestDto userRequestDto);
    Optional<User> findByEmail(String email);
    UserResponseDto getUserResponseDtoById(Long id);
    UserResponseDto getUserResponseDtoByEmail(String email);
    User getUser();
    User saveUser(User user);
}