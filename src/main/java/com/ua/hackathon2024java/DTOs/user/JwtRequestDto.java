package com.ua.hackathon2024java.DTOs.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequestDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}