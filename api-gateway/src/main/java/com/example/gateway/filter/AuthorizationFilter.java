package com.example.gateway.filter;

import com.example.gateway.core.exception.GatewayException;
import com.example.gateway.core.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements ServerSecurityContextRepository {

    private final JwtService jwtService;
    private final RouteValidator validator;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {

        ServerHttpRequest req = serverWebExchange.getRequest();

        if (validator.isSecured.test(req)) {
            try {
                String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring(7);
                boolean hasAccess = jwtService.hasAcess(authHeader);

                if (!hasAccess) {
                    throw new GatewayException("Authorization header is invalid");
                }

                final String user = jwtService.extractUsername(authHeader);
                final String authorities = jwtService.extractClaim(authHeader).get("authorities").toString();

                req.mutate().header("loggendInUser", user).build();
                req.mutate().header("authorities", authorities).build();

                return Mono.just(new SecurityContextImpl(new UsernamePasswordAuthenticationToken(user, null, parseAuthorities(authorities))));

            } catch (ExpiredJwtException e) {
                throw new GatewayException("Authorization header has expired");
            } catch (JwtException e) {
                throw new GatewayException("Authorization header is invalid");
            }

        }
        return Mono.empty();
    }

    private List<? extends GrantedAuthority> parseAuthorities(String authorities) {

        Collection<String> authorityList = Arrays.asList(authorities.split(","));

        return authorityList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
