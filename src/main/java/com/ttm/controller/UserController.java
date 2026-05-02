package com.ttm.controller;

import com.ttm.dto.Dtos.UserBrief;
import com.ttm.entity.User;
import com.ttm.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository users;
    public UserController(UserRepository users) { this.users = users; }

    @GetMapping
    public List<UserBrief> all() {
        return users.findAll().stream()
            .map(u -> new UserBrief(u.getId(), u.getName(), u.getEmail(), u.getRole().name()))
            .toList();
    }

    @GetMapping("/me")
    public UserBrief me(@AuthenticationPrincipal User u) {
        return new UserBrief(u.getId(), u.getName(), u.getEmail(), u.getRole().name());
    }
}
