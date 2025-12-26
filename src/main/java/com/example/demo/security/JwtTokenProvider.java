package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long validityInMs;
    private final boolean includeRole;

    // ✅ REQUIRED by tests
    public JwtTokenProvider(String secretKey, long validityInMs, boolean includeRole) {
        this.secretKey = secretKey;
        this.validityInMs = validityInMs;
        this.includeRole = includeRole;
    }

    // ✅ Required for Spring
    public JwtTokenProvider() {
        this("mySecretKey123456", 3600000, true);
    }

    public String generateToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);

        if (includeRole) {
            claims.put("role", role);
        }

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRole(String token) {
        Object role = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role");

        return role != null ? role.toString() : null;
    }
}
