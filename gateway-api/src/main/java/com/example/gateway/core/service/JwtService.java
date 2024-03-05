package com.example.gateway.core.service;

import com.example.gateway.core.util.EnvUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;

import static com.example.gateway.core.constants.Constants.AUTHORITIES;
import static com.example.gateway.core.constants.Constants.AUTH_SECRET_TOKEN;
import static com.example.gateway.core.util.NullUtil.isNotNull;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final EnvUtil env;

    public void validateToken(final String token) {
        getClaims(token);
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty(AUTH_SECRET_TOKEN));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean hasAcess(String token) {
        String user = extractUsername(token);
        return user != null;
    }

    public String extractUsername(String token) {
        return getClaims(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();

        Object authoritiesClaim = claims.get(AUTHORITIES);

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;

        if (isNotNull(authoritiesClaim)) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());
        }

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }

}
