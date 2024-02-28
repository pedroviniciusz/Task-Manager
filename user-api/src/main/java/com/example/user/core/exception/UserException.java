package com.example.user.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3344534100275479290L;

    public UserException(String msg) {
        super(msg);
    }

}
