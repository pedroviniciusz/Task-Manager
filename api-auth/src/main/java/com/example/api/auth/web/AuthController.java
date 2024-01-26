package com.example.api.auth.web;

import com.example.api.auth.core.entity.User;
import com.example.api.auth.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/token")
    public String getToken(@RequestBody User user) {
        return service.generateToken(user.getUsername());
    }

    @GetMapping("/validate")
    public String validadeToken(@RequestParam("token") String token) {
        service.validadeToken(token);
        return "Token válido";
    }

}
