package com.example.api.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teste")
public class Controller {

    @GetMapping
    public <T> ResponseEntity<T> teste() {
        return ResponseEntity.ok().build();
    }

}
