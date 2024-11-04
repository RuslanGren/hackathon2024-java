package com.ua.hackathon2024java.DTOs.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Username should not be empty!")
    @Size(min = 5, max = 30, message = "Username should be between 5 and 30 characters")
    private String username;

    @NotBlank(message = "Email should not be empty!")
    @Email
    @Size(min = 5, max = 30, message = "Email should be between 5 and 30 characters")
    private String email;

    @NotBlank(message = "Password should not be empty!")
    @Size(min = 4, max = 30, message = "Password should be between 4 and 30 characters")
    private String password;

    @NotEmpty(message = "At least one role must be provided!")
    private Set<String> roles;
}