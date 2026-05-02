package com.ttm.controller;

import com.ttm.dto.Dtos.*;
import com.ttm.entity.Project;
import com.ttm.entity.Task;
import com.ttm.entity.User;
import com.ttm.repository.ProjectRepository;
import com.ttm.repository.TaskRepository;
import com.ttm.repository.UserRepository;
import com.ttm.service.AccessService;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskRepository tasks;
    private final ProjectRepository projects;
    private final UserRepository users;
    private final AccessService access;

    public TaskController(TaskRepository tasks, ProjectRepository projects, UserRepository users, AccessService access) {
        this.tasks = tasks; this.projects = projects; this.users = users; this.access = access;
    }

    private TaskResponse toDto(Task t) {
        boolean overdue = t.getDueDate() != null
            && t.getStatus() != Task.Status.DONE
            && t.getDueDate().isBefore(LocalDate.now());
        return new TaskResponse(
            t.getId(), t.getTitle(), t.getDescription(),
            t.getStatus().name(), t.getPriority().name(), t.getDueDate(),
            t.getProject().getId(), t.getProject().getName(),
            t.getAssignee() != null ? t.getAssignee().getId() : null,
            t.getAssignee() != null ? t.getAssignee().getName() : null,
            overdue
        );
    }

    @GetMapping("/projects/{projectId}/tasks")
    public List<TaskResponse> listForProject(@PathVariable Long projectId, @AuthenticationPrincipal User u) {
        Project p = projects.findById(projectId).orElseThrow(() -> new IllegalStateException("Project not found"));
        access.requireMember(p, u);
        return tasks.findByProjectId(projectId).stream().map(this::toDto).toList();
    }

    @PostMapping("/projects/{projectId}/tasks")
    public TaskResponse create(@PathVariable Long projectId, @Valid @RequestBody TaskRequest req,
                               @AuthenticationPrincipal User u) {
        Project p = projects.findById(projectId).orElseThrow(() -> new IllegalStateException("Project not found"));
        access.requireMember(p, u);
        // Only admin or project owner can create tasks
        if (!access.isAdmin(u) && !p.getOwner().getId().equals(u.getId()))
            throw new AccessDeniedException("Only admin or project owner can create tasks");

        Task t = Task.builder()
            .title(req.title())
            .description(req.description())
            .status(req.status() != null ? Task.Status.valueOf(req.status()) : Task.Status.TODO)
            .priority(req.priority() != null ? Task.Priority.valueOf(req.priority()) : Task.Priority.MEDIUM)
            .dueDate(req.dueDate())
            .project(p)
            .assignee(req.assigneeId() != null ? users.findById(req.assigneeId()).orElse(null) : null)
            .build();
        return toDto(tasks.save(t));
    }

    @PutMapping("/tasks/{id}")
    public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskRequest req,
                               @AuthenticationPrincipal User u) {
        Task t = tasks.findById(id).orElseThrow(() -> new IllegalStateException("Task not found"));
        access.requireMember(t.getProject(), u);
        if (!access.isAdmin(u) && !t.getProject().getOwner().getId().equals(u.getId()))
            throw new AccessDeniedException("Only admin or project owner can edit tasks");
        t.setTitle(req.title());
        t.setDescription(req.description());
        if (req.status() != null) t.setStatus(Task.Status.valueOf(req.status()));
        if (req.priority() != null) t.setPriority(Task.Priority.valueOf(req.priority()));
        t.setDueDate(req.dueDate());
        t.setAssignee(req.assigneeId() != null ? users.findById(req.assigneeId()).orElse(null) : null);
        return toDto(tasks.save(t));
    }

    // Members can update status of tasks assigned to them
    @PatchMapping("/tasks/{id}/status")
    public TaskResponse updateStatus(@PathVariable Long id, @Valid @RequestBody TaskStatusRequest req,
                                     @AuthenticationPrincipal User u) {
        Task t = tasks.findById(id).orElseThrow(() -> new IllegalStateException("Task not found"));
        access.requireMember(t.getProject(), u);
        boolean canEdit = access.isAdmin(u)
            || t.getProject().getOwner().getId().equals(u.getId())
            || (t.getAssignee() != null && t.getAssignee().getId().equals(u.getId()));
        if (!canEdit) throw new AccessDeniedException("Cannot update this task");
        t.setStatus(Task.Status.valueOf(req.status()));
        return toDto(tasks.save(t));
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User u) {
        Task t = tasks.findById(id).orElseThrow(() -> new IllegalStateException("Task not found"));
        if (!access.isAdmin(u) && !t.getProject().getOwner().getId().equals(u.getId()))
            throw new AccessDeniedException("Only admin or project owner can delete tasks");
        tasks.delete(t);
    }

    @GetMapping("/tasks/mine")
    public List<TaskResponse> mine(@AuthenticationPrincipal User u) {
        return tasks.findByAssignee(u).stream().map(this::toDto).toList();
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(@AuthenticationPrincipal User u) {
        List<Task> all;
        if (access.isAdmin(u)) {
            all = tasks.findAll();
        } else {
            all = projects.findAllForUser(u).stream()
                .flatMap(p -> tasks.findByProjectId(p.getId()).stream())
                .toList();
        }
        long total = all.size();
        long todo = all.stream().filter(t -> t.getStatus() == Task.Status.TODO).count();
        long inProgress = all.stream().filter(t -> t.getStatus() == Task.Status.IN_PROGRESS).count();
        long done = all.stream().filter(t -> t.getStatus() == Task.Status.DONE).count();
        LocalDate today = LocalDate.now();
        long overdue = all.stream().filter(t -> t.getDueDate() != null
            && t.getStatus() != Task.Status.DONE
            && t.getDueDate().isBefore(today)).count();
        long mine = all.stream().filter(t -> t.getAssignee() != null && t.getAssignee().getId().equals(u.getId())).count();
        return new DashboardResponse(total, todo, inProgress, done, overdue, mine);
    }
}
