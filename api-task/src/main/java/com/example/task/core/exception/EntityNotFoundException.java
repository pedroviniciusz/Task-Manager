package com.example.task.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4221132214346019544L;

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
