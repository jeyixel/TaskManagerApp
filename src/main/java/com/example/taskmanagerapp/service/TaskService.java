package com.example.taskmanagerapp.service;

import com.example.taskmanagerapp.entity.Task;
import com.example.taskmanagerapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    // Best Practice: Constructor Injection
    // Instead of using @Autowired on the field, we use the constructor.
    // This makes testing easier and ensures the service can't run without a repository.
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Return all tasks
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    // 3. Read One
    public Optional<Task> getTaskById(Long id) {
        // Returns an "Optional" because the task might not exist.
        // This forces the Controller to handle the "404 Not Found" scenario.
        return taskRepository.findById(id);
    }

    // 4. Update function
    public Task updateTask(Long id, Task taskDetails){
        return taskRepository.findById(id).map(existingTask ->{
            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setDescription(taskDetails.getDescription());
            existingTask.setCompleted(taskDetails.isCompleted());
            return taskRepository.save(existingTask);
        }).orElse(null);
    }

    // 5. Delete
    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
