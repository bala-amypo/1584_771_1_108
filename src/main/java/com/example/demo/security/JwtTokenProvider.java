package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import java.util.*;

public class JwtTokenProvider {

    private final String secret;
    private final long validity;

    public JwtTokenProvider(String secret, long validity, boolean ignored) {
        this.secret = secret;
        this.validity = validity;
    }

    public String generateToken(Authentication auth,
                                Long userId,
                                String role) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        claims.put("email", auth.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis()+validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return (String) getAllClaims(token).get("email");
    }

    public Map<String,Object> getAllClaims(String token) {
        Claims c = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return new HashMap<>(c);
    }
}
