package com.example.api.auth.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static com.example.api.auth.core.constants.Constants.AUTHORITIES;
import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${auth.expiration.time}")
    private String authExpirationTime;

    @Value("${auth.secret.token}")
    private String authSecretToken;

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Claims claims = Jwts.claims().setSubject(username);

        claims.put(AUTHORITIES, authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(",")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(authExpirationTime)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(authSecretToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
