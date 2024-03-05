package com.example.task.web.controller;

import com.example.task.core.entity.Task;
import com.example.task.core.entity.User;
import com.example.task.core.service.TaskService;
import com.example.task.core.service.UserService;
import com.example.task.web.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Gets task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve the task"),
            @ApiResponse(responseCode = "404", description = "Task not found")})
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable int id) {
        return writeResponseBody(TaskDto.transferToDto(service.findTaskById(id)));
    }

    @Operation(summary = "Gets the logged in user tasks that will expire in one day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with this tasks"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/expiring-tomorrow")
    public ResponseEntity<List<TaskDto>> findTasksExpiringTomorrow(@RequestHeader(name = "loggedInUser") String username) {
        User user = userService.findByUsername(username);
        return writeResponseBody(TaskDto.transferToDtoList(service.findTasksExpiringTomorrow(user.getId())));
    }

    @Operation(summary = "Gets the logged in user tasks that have already expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with this tasks"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/expired")
    public ResponseEntity<List<TaskDto>> findExpiredTasks(@RequestHeader(name = "loggedInUser") String username) {
        User user = userService.findByUsername(username);
        return writeResponseBody(TaskDto.transferToDtoList(service.findExpiredTasks(user.getId())));
    }

    @Operation(summary = "Gets the all logged in user tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with this tasks"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/user")
    public ResponseEntity<List<TaskDto>> findByLoggedInUser(@RequestHeader(name = "loggedInUser") String username) {
        User user = userService.findByUsername(username);
        return writeResponseBody(TaskDto.transferToDtoList(service.findTaskByUser(user.getId())));
    }

    @Operation(summary = "Creates a task")
    @PostMapping
    public void createTask(@RequestBody Task task) {
        service.createTask(task);
    }

    @Operation(summary = "Updates a task by id")
    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        service.updateTask(id, updatedTask);
    }

    @Operation(summary = "Delete a task by id")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        service.deleteTask(id);
    }

}
