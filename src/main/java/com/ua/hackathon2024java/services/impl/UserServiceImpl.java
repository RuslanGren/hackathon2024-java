package com.ua.hackathon2024java.services.impl;


import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.entity.Regions;
import com.ua.hackathon2024java.entity.Role;
import com.ua.hackathon2024java.entity.User;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.exceptions.DatabaseException;
import com.ua.hackathon2024java.exceptions.UserNotFoundException;
import com.ua.hackathon2024java.factories.UserDtoFactory;
import com.ua.hackathon2024java.repository.UserRepository;
import com.ua.hackathon2024java.services.RoleService;
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
    private final RoleService roleService;

    @Override
    public List<User> findAllUsersByRegion(Regions region) {
        Role role = roleService.findByName(region.name()).orElseThrow(() -> new BadRequestException("Role not found"));
        return userRepository.findAllByRoles(role);
    }

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
    public String deleteUserById(Long id) {
        try {
            userRepository.delete(findById(id));
        } catch (Exception e) {
            throw new DatabaseException();
        }

        return "User successfully deleted";
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUserResponseDto() {
        return userRepository.findAll()
                .stream()
                .map(userDtoFactory::makeUserResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public String updateUserRoles(Long userId, Set<String> rolesName) {
        User user = findById(userId);

        Set<Role> roles = roleService.findRolesByName(rolesName);

        user.setRoles(roles);
        saveUser(user);

        return "Roles successfully updated";
    }

    @Override
    @Transactional
    public UserResponseDto createNewUser(UserRequestDto userRequestDto) {
        if (findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException("User already exists");
        }

        Set<Role> roles = roleService.findRolesByName(userRequestDto.getRoles());

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .roles(roles)
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
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
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