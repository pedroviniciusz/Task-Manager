package com.example.task.web.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.task.core.util.NullUtil.isNotNull;

public abstract class BaseRestController {

    protected <T> ResponseEntity<List<T>> writeResponseBody(List<T> body) {
        if (isNotNull(body)) {
            return ResponseEntity.ok(body);
        }
        return ResponseEntity.noContent().build();
    }

    protected <T> ResponseEntity<T> writeResponseBody(T body) {
        return ResponseEntity.ok(body);
    }

    protected <T> ResponseEntity<T> writeResponseBody() {
        return ResponseEntity.ok().build();
    }
}
