package com.example.gateway.filter;

import com.example.gateway.core.exception.GatewayException;
import com.example.gateway.core.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

import static com.example.gateway.core.constants.Constants.LOGGED_IN_USER;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest req = exchange.getRequest();

        if (RouteValidator.isSecured.test(req)) {
            try {
                final String authHeader = Objects.requireNonNull(req.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).substring(7);

                boolean hasAccess = jwtService.hasAcess(authHeader);

                if (!hasAccess) {
                    throw new GatewayException("Authorization header is invalid");
                }

                req.mutate().header(LOGGED_IN_USER, jwtService.extractUsername(authHeader)).build();

                return Mono.fromCallable(() -> this.jwtService.getAuthentication(authHeader))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(authentication -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));

            } catch (ExpiredJwtException e) {
                throw new GatewayException("Authorization header has expired");
            } catch (JwtException e) {
                throw new GatewayException("Authorization header is invalid");
            } catch (NullPointerException e) {
                throw new GatewayException("Missing authorization header");
            }
        }
        return chain.filter(exchange);
    }

}
