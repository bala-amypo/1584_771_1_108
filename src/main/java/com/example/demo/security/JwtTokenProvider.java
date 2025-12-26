package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {

    private final String secret;
    private final long expiration;
    private final boolean debug;

    // REQUIRED BY TEST
    public JwtTokenProvider(String secret, long expiration, boolean debug) {
        this.secret = secret;
        this.expiration = expiration;
        this.debug = debug;
    }

    // REQUIRED BY TEST
    public String generateToken(Authentication authentication, long userId, String role) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(Map.of("userId", userId, "role", role))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // REQUIRED BY JwtAuthenticationFilter
    public boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // REQUIRED BY JwtAuthenticationFilter
    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    // REQUIRED BY TEST
    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    // REQUIRED BY TEST
    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
