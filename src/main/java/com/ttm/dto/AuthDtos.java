package com.ttm.dto;

import jakarta.validation.constraints.*;

public class AuthDtos {
    public record SignupRequest(
        @NotBlank @Size(max=100) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min=6, max=100) String password,
        String role // optional: ADMIN or MEMBER, default MEMBER
    ) {}

    public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
    ) {}

    public record AuthResponse(String token, Long id, String name, String email, String role) {}
}
