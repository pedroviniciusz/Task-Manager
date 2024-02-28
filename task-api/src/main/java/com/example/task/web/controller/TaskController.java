package com.example.task.web.controller;

import com.example.task.core.entity.Task;
import com.example.task.core.entity.User;
import com.example.task.core.service.TaskService;
import com.example.task.core.service.UserService;
import com.example.task.web.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController extends BaseRestController {

    private final TaskService service;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable int id) {
        return writeResponseBody(TaskDto.transferToDto(service.findTaskById(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskDto>> findByLoggedInUser(@RequestHeader(name = "loggendInUser") String username) {
        User user = userService.findByUsername(username);
        return writeResponseBody(TaskDto.transferToDtoList(service.findTaskByUser(user.getId())));
    }

    @PostMapping
    public void createTask(@RequestBody Task task) {
        service.createTask(task);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        service.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        service.deleteTask(id);
    }

}
