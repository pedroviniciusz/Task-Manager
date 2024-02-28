package com.example.gateway.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class GatewayException extends RuntimeException {

    public GatewayException(String msg) {
        super(msg);
    }
}
