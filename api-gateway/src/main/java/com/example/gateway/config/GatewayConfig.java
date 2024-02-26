package com.example.gateway.config;

import com.example.gateway.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthorizationFilter authorizationFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(path -> path
                        .pathMatchers("/api/auth/token",
                                "/api/auth/validate",
                                "/eureka",
                                "/actuator/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/*/*/v3/api-docs").permitAll()
                        .anyExchange().authenticated()
                )
                .securityContextRepository(authorizationFilter)
                .build();
    }
}
