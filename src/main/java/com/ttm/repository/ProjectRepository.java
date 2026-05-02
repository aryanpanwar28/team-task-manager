package com.ttm.repository;

import com.ttm.entity.Project;
import com.ttm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select distinct p from Project p left join p.members m where p.owner = :user or m = :user")
    List<Project> findAllForUser(@Param("user") User user);
}
