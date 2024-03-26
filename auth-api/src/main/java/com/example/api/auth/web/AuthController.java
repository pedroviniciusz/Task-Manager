package com.example.api.auth.web;

import com.example.api.auth.core.entity.User;
import com.example.api.auth.core.exception.AuthException;
import com.example.api.auth.core.messages.Messages;
import com.example.api.auth.core.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.api.auth.core.messages.Messages.BAD_CREDENTIALS;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService service;
    private final AuthenticationManager authenticationManager;
    private final Messages messages;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody User user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                responseHeaders.set(HttpHeaders.AUTHORIZATION, service.generateToken(authentication));
            }
        } catch (AuthenticationException e) {
            throw new AuthException(messages.getMessage(BAD_CREDENTIALS));
        }
        return ResponseEntity.ok().headers(responseHeaders).body("");
    }

}
