package com.ttm.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Task() {}

    public Task(Long id, String title, String description, Status status, Priority priority, LocalDate dueDate, Project project, User assignee, Instant createdAt) {
        this.id = id; this.title = title; this.description = description; this.status = status; this.priority = priority; this.dueDate = dueDate; this.project = project; this.assignee = assignee; this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public User getAssignee() { return assignee; }
    public void setAssignee(User assignee) { this.assignee = assignee; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        if (this.status == null) this.status = Status.TODO;
        if (this.priority == null) this.priority = Priority.MEDIUM;
    }

    public enum Status { TODO, IN_PROGRESS, DONE }
    public enum Priority { LOW, MEDIUM, HIGH }

    // Simple builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private String title; private String description; private Status status; private Priority priority; private LocalDate dueDate; private Project project; private User assignee; private Instant createdAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder priority(Priority priority) { this.priority = priority; return this; }
        public Builder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
        public Builder project(Project project) { this.project = project; return this; }
        public Builder assignee(User assignee) { this.assignee = assignee; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Task build() { return new Task(id, title, description, status, priority, dueDate, project, assignee, createdAt); }
    }
}