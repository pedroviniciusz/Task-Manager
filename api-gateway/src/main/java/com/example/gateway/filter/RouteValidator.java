package com.example.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/token",
            "/api/auth/validate",
            "/eureka",
            "/actuator",
            "/webjars",
            "/v3/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
