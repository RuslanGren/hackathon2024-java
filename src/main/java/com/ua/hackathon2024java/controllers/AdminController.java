package com.ua.hackathon2024java.controllers;

import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/users")
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.createNewUser(userRequestDto);
    }

    @PutMapping("/users/{userId}/roles")
    public String updateUserRole(
            @PathVariable Long userId,
            @RequestBody Set<String> newRolesName
    ) {
        return userService.updateUserRoles(userId, newRolesName);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAllUserResponseDto();
    }
}