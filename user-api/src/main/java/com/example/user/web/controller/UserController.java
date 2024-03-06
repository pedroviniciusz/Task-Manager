package com.example.user.web.controller;

import com.example.user.core.entity.User;
import com.example.user.core.service.UserService;
import com.example.user.web.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController extends BaseRestController {

    private final UserService service;

    @Operation(summary = "Gets user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve the user"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        return writeResponseBody(UserDto.transferToDto(service.findUserById(id)));
    }

    @Operation(summary = "Gets user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve the user"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username) {
        return writeResponseBody(UserDto.transferToDto(service.findUserByUsername(username)));
    }

    @Operation(summary = "Gets all the users, only admins can access this endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieve a list with the users"),
            @ApiResponse(responseCode = "204", description = "Retrieve an empty list")})
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findById() {
        return writeResponseBody(UserDto.transferToDtoList(service.findAllUsers()));
    }

    @Operation(summary = "Creates an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user has been created successfully"),
            @ApiResponse(responseCode = "400", description = "An error has occurred while creating the user")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        service.createUser(user);
    }

    @Operation(summary = "Updates an user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been updated successfully"),
            @ApiResponse(responseCode = "400", description = "An error has occurred while updating the user"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        service.updateUser(id, updatedUser);
    }

    @Operation(summary = "Deletes an user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user has been deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }

    @Operation(summary = "Update just the CPF value in user by id, only admins can access this endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user's cpf has been updated successfully"),
            @ApiResponse(responseCode = "400", description = "An error has occurred while updating user's cpf"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PatchMapping("/update-cpf/{id}")
    public void updateCpf(@PathVariable int id, @RequestBody String cpf) {
        service.updateCpf(id, cpf);
    }

}
