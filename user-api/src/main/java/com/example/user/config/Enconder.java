package com.example.user.config;

import com.example.user.core.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Enconder {

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public void encodePassword(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
    }
}
