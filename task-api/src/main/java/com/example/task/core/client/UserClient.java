package com.example.task.core.client;

import com.example.task.core.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-API", path = "/api/user")
public interface UserClient {

    @GetMapping("/username/{username}")
    ResponseEntity<User> findByUsername(@PathVariable String username);

}
