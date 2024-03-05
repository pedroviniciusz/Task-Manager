package com.example.gateway.config;

import com.example.gateway.core.service.JwtService;
import com.example.gateway.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewayConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, JwtService jwtService) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(path -> {
                            path.pathMatchers(HttpMethod.POST, "/api/user").permitAll();
                            path.pathMatchers(HttpMethod.GET, "/api/user/all").hasRole("ADMIN");
                            path.pathMatchers(HttpMethod.PATCH, "/api/user/update-cpf/**").hasRole("ADMIN");
                            path.pathMatchers("/api/auth/token",
                                            "/api/auth/validate",
                                            "/eureka",
                                            "/actuator/**",
                                            "/webjars/**",
                                            "/v3/api-docs/**",
                                            "/*/*/v3/api-docs",
                                            "/swagger-ui.html").permitAll()
                                    .anyExchange().authenticated();
                        }
                )
                .addFilterAt(new AuthorizationFilter(jwtService), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }
}
