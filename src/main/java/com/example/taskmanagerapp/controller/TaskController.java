package com.example.taskmanagerapp.controller;

import com.example.taskmanagerapp.entity.Task;
import com.example.taskmanagerapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // tell spring this class handles web requests
@RequestMapping("/api/tasks") // Base URL for all endpoints in this controller
@CrossOrigin(origins = "http://localhost:3000") // Allow the react app to talk to this API
public class TaskController {
    private final TaskService taskService;

    // constructor injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // GET one task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                // Where does "task" come from? Its the lambda parameter representing the found task
                .map(task -> ResponseEntity.ok(task)) // return 200 OK with task
                .orElse(ResponseEntity.notFound().build()); // return 404 Not Found
    }

    // POST create a new task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // DELETE a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
