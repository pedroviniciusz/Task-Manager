package com.example.api.auth.core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvUtil {

    private final Environment environment;

    public String getProperty(String key) {
        return environment.getProperty(key);
    }
}