package com.example.user.web.controller;

import com.example.user.core.entity.User;
import com.example.user.core.service.UserService;
import com.example.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseRestController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        return writeResponseBody(UserDto.transferToDto(service.findUserById(id)));
    }

    @PostMapping
    public void createTask(@RequestBody User user) {
        service.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        service.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteUser(id);
    }

}
