package com.example.api.auth.web;

import com.example.api.auth.core.entity.User;
import com.example.api.auth.core.exception.AuthException;
import com.example.api.auth.core.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                responseHeaders.set(HttpHeaders.AUTHORIZATION, service.generateToken(authentication));
            }
        } catch (AuthenticationException e) {
            throw new AuthException("Invalid acess");
        }
        return ResponseEntity.ok().headers(responseHeaders).body("");
    }

    @GetMapping("/validate")
    public String validadeToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token válido";
    }

}
