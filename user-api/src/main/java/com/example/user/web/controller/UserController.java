package com.example.user.web.controller;

import com.example.user.core.entity.User;
import com.example.user.core.service.UserService;
import com.example.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController extends BaseRestController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        return writeResponseBody(UserDto.transferToDto(service.findUserById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findById() {
        return writeResponseBody(UserDto.transferToDtoList(service.findAllUsers()));
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

    @PatchMapping("/update-cpf/{id}")
    public void updateCpf(@PathVariable int id, @RequestBody String cpf) {
        service.updateCpf(id, cpf);
    }

}
