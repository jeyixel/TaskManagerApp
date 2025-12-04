package com.example.taskmanagerapp.repository;

import com.example.taskmanagerapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> { // Long is the type of the primary key, Task is the entity type
    // No code needed! Spring implements this for us at runtime.
}