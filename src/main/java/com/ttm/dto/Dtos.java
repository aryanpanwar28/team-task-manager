package com.ttm.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class Dtos {
    public record ProjectRequest(
        @NotBlank @Size(max=150) String name,
        @Size(max=1000) String description,
        Set<Long> memberIds
    ) {}

    public record ProjectResponse(Long id, String name, String description,
                                  Long ownerId, String ownerName,
                                  Set<UserBrief> members) {}

    public record UserBrief(Long id, String name, String email, String role) {}

    public record TaskRequest(
        @NotBlank @Size(max=200) String title,
        @Size(max=2000) String description,
        String status,   // TODO / IN_PROGRESS / DONE
        String priority, // LOW / MEDIUM / HIGH
        LocalDate dueDate,
        Long assigneeId
    ) {}

    public record TaskStatusRequest(@NotBlank String status) {}

    public record TaskResponse(Long id, String title, String description,
                               String status, String priority, LocalDate dueDate,
                               Long projectId, String projectName,
                               Long assigneeId, String assigneeName,
                               boolean overdue) {}

    public record DashboardResponse(long total, long todo, long inProgress, long done,
                                    long overdue, long myTasks) {}
}
