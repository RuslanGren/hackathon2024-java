package com.ua.hackathon2024java.services.impl;

import com.ua.hackathon2024java.DTOs.user.JwtRequestDto;
import com.ua.hackathon2024java.DTOs.user.JwtResponseDto;
import com.ua.hackathon2024java.DTOs.user.UserRequestDto;
import com.ua.hackathon2024java.DTOs.user.UserResponseDto;
import com.ua.hackathon2024java.config.CustomUserDetailsService;
import com.ua.hackathon2024java.config.JwtTokenUtils;
import com.ua.hackathon2024java.exceptions.BadRequestException;
import com.ua.hackathon2024java.exceptions.UnauthorizedException;
import com.ua.hackathon2024java.services.AuthService;
import com.ua.hackathon2024java.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public JwtResponseDto createAuthToken(JwtRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Email or password is wrong");
        }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtTokenUtils.createToken(userDetails);

            return new JwtResponseDto(userService.getUserResponseDtoByEmail(request.getEmail()), token);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponseDto registerNewUser(UserRequestDto userRequestDto) {
        if (!userRequestDto.getPassword().equals(userRequestDto.getRepeatPassword())) {
            throw new BadRequestException("Passwords don't match");
        }

        if (userService.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException("User already exists");
        }

        return userService.createNewUser(userRequestDto);
    }


}