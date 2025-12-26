package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long validityInMs;
    private final boolean includeRole;

    // ✅ REQUIRED by your tests
    public JwtTokenProvider(String secret, long validityInMs, boolean includeRole) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMs = validityInMs;
        this.includeRole = includeRole;
    }

    // ✅ Default constructor for Spring
    public JwtTokenProvider() {
        this("my-secret-key-my-secret-key-my-secret-key", 3600000, true);
    }

    public String generateToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);

        if (includeRole) {
            claims.put("role", "ROLE_" + role);
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
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
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
