package com.example.task.web.controller;

import com.example.task.core.entity.Task;
import com.example.task.core.service.TaskService;
import com.example.task.web.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController extends BaseRestController {

    private final TaskService service;

    @Operation(summary = "Gets task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve the task"),
            @ApiResponse(responseCode = "404", description = "Task not found")})
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable int id) {
        return writeResponseBody(TaskDto.transferToDto(service.findTaskById(id)));
    }

    @Operation(summary = "Gets the logged in user tasks that will expires in one day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with this tasks"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/expiring-tomorrow")
    public ResponseEntity<List<TaskDto>> findTasksExpiringTomorrow(@RequestHeader(name = "loggedInUser") String username) {
        return writeResponseBody(TaskDto.transferToDtoList(service.findTasksExpiringTomorrow(username)));
    }

    @Operation(summary = "Gets the logged in user tasks that have already expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with this tasks"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/expired")
    public ResponseEntity<List<TaskDto>> findExpiredTasks(@RequestHeader(name = "loggedInUser") String username) {
        return writeResponseBody(TaskDto.transferToDtoList(service.findExpiredTasks(username)));
    }

    @Operation(summary = "Gets the all logged in user tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with this tasks"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/user")
    public ResponseEntity<List<TaskDto>> findByLoggedInUser(@RequestHeader(name = "loggedInUser") String username) {
        return writeResponseBody(TaskDto.transferToDtoList(service.findTaskByUser(username)));
    }

    @Operation(summary = "Creates a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The task has been created successfully"),
            @ApiResponse(responseCode = "400", description = "An error has occurred while creating the task")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createTask(@RequestBody Task task) {
        service.createTask(task);
    }

    @Operation(summary = "Updates a task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The task has been updated successfully"),
            @ApiResponse(responseCode = "400", description = "An error has occurred while updating the task"),
            @ApiResponse(responseCode = "404", description = "Task not found")})
    @PutMapping("/{id}")
    public void updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        service.updateTask(id, updatedTask);
    }

    @Operation(summary = "Delete a task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The task has been deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")})
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        service.deleteTask(id);
    }

}
