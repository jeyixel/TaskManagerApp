package com.example.taskmanagerapp.controller;

import com.example.taskmanagerapp.entity.Task;
import com.example.taskmanagerapp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 1. Tells Spring this class handles Web Requests
@RequestMapping("/api/tasks") // 2. Base URL (e.g., http://localhost:8080/api/tasks)
@CrossOrigin(origins = "http://localhost:5173") // 3. Allows your React app to talk to this API
public class TaskController {

    private final TaskService taskService;

    // Best Practice: Constructor Injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // GET single task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> ResponseEntity.ok(task)) // Return 200 OK with data
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found
    }

    // POST create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        // Return 201 CREATED status code (Best Practice for POST)
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // PUT update a task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);

        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content (Best Practice for DELETE)
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}