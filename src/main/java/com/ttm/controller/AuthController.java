package com.ttm.controller;

import com.ttm.dto.AuthDtos.*;
import com.ttm.entity.User;
import com.ttm.repository.UserRepository;
import com.ttm.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users; this.encoder = encoder; this.jwt = jwt;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        if (users.existsByEmail(req.email()))
            throw new IllegalArgumentException("Email already registered");
        User.Role role = User.Role.MEMBER;
        if (req.role() != null && req.role().equalsIgnoreCase("ADMIN")) role = User.Role.ADMIN;
        User u = User.builder()
            .name(req.name())
            .email(req.email())
            .password(encoder.encode(req.password()))
            .role(role)
            .build();
        users.save(u);
        String token = jwt.generate(u.getEmail(), u.getRole().name(), u.getId());
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User u = users.findByEmail(req.email())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!encoder.matches(req.password(), u.getPassword()))
            throw new IllegalArgumentException("Invalid credentials");
        String token = jwt.generate(u.getEmail(), u.getRole().name(), u.getId());
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getName(), u.getEmail(), u.getRole().name()));
    }
}
