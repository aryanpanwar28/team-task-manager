package com.ttm.controller;

import com.ttm.dto.Dtos.*;
import com.ttm.entity.Project;
import com.ttm.entity.User;
import com.ttm.repository.ProjectRepository;
import com.ttm.repository.UserRepository;
import com.ttm.service.AccessService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projects;
    private final UserRepository users;
    private final AccessService access;

    public ProjectController(ProjectRepository projects, UserRepository users, AccessService access) {
        this.projects = projects; this.users = users; this.access = access;
    }

    private ProjectResponse toDto(Project p) {
        Set<UserBrief> members = p.getMembers().stream()
            .map(m -> new UserBrief(m.getId(), m.getName(), m.getEmail(), m.getRole().name()))
            .collect(Collectors.toSet());
        return new ProjectResponse(p.getId(), p.getName(), p.getDescription(),
            p.getOwner().getId(), p.getOwner().getName(), members);
    }

    @GetMapping
    public List<ProjectResponse> list(@AuthenticationPrincipal User u) {
        List<Project> list = access.isAdmin(u) ? projects.findAll() : projects.findAllForUser(u);
        return list.stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ProjectResponse get(@PathVariable Long id, @AuthenticationPrincipal User u) {
        Project p = projects.findById(id).orElseThrow(() -> new IllegalStateException("Project not found"));
        access.requireMember(p, u);
        return toDto(p);
    }

    @PostMapping
    public ProjectResponse create(@Valid @RequestBody ProjectRequest req, @AuthenticationPrincipal User u) {
        if (!access.isAdmin(u)) throw new org.springframework.security.access.AccessDeniedException("Admins only");
        Set<User> members = new HashSet<>();
        if (req.memberIds() != null) {
            members.addAll(users.findAllById(req.memberIds()));
        }
        Project p = Project.builder()
            .name(req.name())
            .description(req.description())
            .owner(u)
            .members(members)
            .build();
        return toDto(projects.save(p));
    }

    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable Long id, @Valid @RequestBody ProjectRequest req,
                                  @AuthenticationPrincipal User u) {
        Project p = projects.findById(id).orElseThrow(() -> new IllegalStateException("Project not found"));
        access.requireOwnerOrAdmin(p, u);
        p.setName(req.name());
        p.setDescription(req.description());
        if (req.memberIds() != null) {
            p.setMembers(new HashSet<>(users.findAllById(req.memberIds())));
        }
        return toDto(projects.save(p));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User u) {
        Project p = projects.findById(id).orElseThrow(() -> new IllegalStateException("Project not found"));
        access.requireOwnerOrAdmin(p, u);
        projects.delete(p);
    }
}
