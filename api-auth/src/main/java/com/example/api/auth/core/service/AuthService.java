package com.example.api.auth.core.service;

import com.example.api.auth.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    public String generateToken(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }

    public void validadeToken(String token){
        jwtService.validateToken(token);
    }

}
