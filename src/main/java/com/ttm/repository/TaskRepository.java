package com.ttm.repository;

import com.ttm.entity.Task;
import com.ttm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssignee(User assignee);

    @Query("select t from Task t where t.project.id = :pid")
    List<Task> tasksOfProject(@Param("pid") Long pid);
}
