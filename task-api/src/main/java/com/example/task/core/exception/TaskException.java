package com.example.task.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TaskException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1795483205523663210L;

    public TaskException(String msg) {
        super(msg);
    }

}
