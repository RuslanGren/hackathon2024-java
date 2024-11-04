package com.ua.hackathon2024java.services;

import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.entity.Role;
import com.ua.hackathon2024java.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    UserResponseDto createNewUser(UserRequestDto userRequestDto);
    Optional<User> findByEmail(String email);
    User findById(Long id);
    UserResponseDto getUserResponseDtoById(Long id);
    UserResponseDto getUserResponseDtoByEmail(String email);
    User getUser();
    User saveUser(User user);
    String deleteUserById(Long id);
    String updateUserRoles(Long userId, Set<String> roles);
    List<UserResponseDto> findAllUserResponseDto();
}