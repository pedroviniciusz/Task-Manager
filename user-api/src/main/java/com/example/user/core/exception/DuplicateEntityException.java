package com.example.user.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateEntityException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 5429551036251395927L;

    public DuplicateEntityException(String msg) {
        super(msg);
    }

}
