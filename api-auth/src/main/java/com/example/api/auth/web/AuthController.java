package com.example.api.auth.web;

import com.example.api.auth.core.entity.User;
import com.example.api.auth.core.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        HttpHeaders responseHeaders = new HttpHeaders();

        if (authentication.isAuthenticated()) {
            responseHeaders.set(HttpHeaders.AUTHORIZATION, service.generateToken(authentication));
            return ResponseEntity.ok().headers(responseHeaders).body("");
        } else {
            throw new RuntimeException("Acesso inválido");
        }

    }

    @GetMapping("/validate")
    public String validadeToken(@RequestParam("token") String token) {
        service.validadeToken(token);
        return "Token válido";
    }

}
