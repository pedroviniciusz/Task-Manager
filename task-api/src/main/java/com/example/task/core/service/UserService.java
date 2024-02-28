package com.example.task.core.service;

import com.example.task.core.entity.User;
import com.example.task.core.exception.TaskException;
import com.example.task.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByUsername(String username){
        return repository.findByUsername(username).orElseThrow(() -> new TaskException("User not found"));
    }

}
