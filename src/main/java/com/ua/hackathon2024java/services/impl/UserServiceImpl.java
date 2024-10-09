package com.ua.hackathon2024java.services.impl;


import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.entity.User;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.exceptions.DatabaseException;
import com.ua.hackathon2024java.exceptions.UserNotFoundException;
import com.ua.hackathon2024java.factories.UserDtoFactory;
import com.ua.hackathon2024java.repository.UserRepository;
import com.ua.hackathon2024java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDtoFactory userDtoFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    @Override
    @Transactional
    public UserResponseDto createNewUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();

        userRepository.save(user);

        return userDtoFactory.makeUserResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserResponseDtoById(Long id) {
        return userDtoFactory
                .makeUserResponseDto(
                        userRepository.findById(id)
                                .orElseThrow(() -> new BadRequestException("User not found"))
                );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserResponseDtoByEmail(String email) {
        return userDtoFactory
                .makeUserResponseDto(
                        userRepository.findByEmail(email)
                                .orElseThrow(() -> new BadRequestException("User not found"))
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser() {
        String userEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository
                .findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }
}