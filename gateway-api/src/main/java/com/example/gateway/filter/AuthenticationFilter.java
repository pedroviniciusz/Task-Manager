package com.example.gateway.filter;

import com.example.gateway.core.exception.GatewayException;
import com.example.gateway.core.messages.Messages;
import com.example.gateway.core.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.gateway.core.constants.Constants.BEARER;
import static com.example.gateway.core.messages.Messages.AUTH_HEADER_IS_INVALID;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private Messages messages;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (RouteValidator.isSecured.test(exchange.getRequest())) {
                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();

                if (authHeader.startsWith(BEARER)) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtService.validateToken(authHeader);
                } catch (Exception e) {
                    throw new GatewayException(messages.getMessage(AUTH_HEADER_IS_INVALID));
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }

}
