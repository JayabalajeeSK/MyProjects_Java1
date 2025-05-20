package com.jb.todo_list_springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jb.todo_list_springboot.model.Task;
import com.jb.todo_list_springboot.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // USER: Get tasks by username
    @GetMapping("/user/tasks")
    public List<Task> getUserTasks(@RequestParam String username) {
        return taskService.getTasksByUsername(username);
    }//

    
    // USER: Create task with username
    @PostMapping("/user/tasks")
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }//

    // USER: Update task (only if username matches)
    @PutMapping("/user/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskService.getTasksById(id).orElseThrow(() -> new RuntimeException("Task with ID " + id + " not found"));

        if (!task.getUsername().equals(updatedTask.getUsername())) {
            throw new RuntimeException("You are not authorized to update this task");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        return ResponseEntity.ok(taskService.saveTask(task));
    }//

    // USER: Delete task (only if username matches)
    @DeleteMapping("/user/tasks/{id}")
    public ResponseEntity<String> deleteUserTask(@PathVariable Long id, @RequestParam String username) {
        Task task = taskService.getTasksById(id)
            .orElseThrow(() -> new RuntimeException("Task with ID " + id + " not found"));

        if (!task.getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this task");
        }

        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }


    //-----------------------------------------------------------

    // ADMIN: Get all tasks
    @GetMapping("/admin/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    } //

    // ADMIN: Delete any task
    @DeleteMapping("/admin/tasks/{id}")
    public ResponseEntity<String> deleteTaskAsAdmin(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok("Task deleted successfully by admin");
}


    // Filter by status
    @GetMapping("/tasks/status")
    public List<Task> filterByStatus(@RequestParam String status) {
        return taskService.getTasksByStatus(status);
    }
}
