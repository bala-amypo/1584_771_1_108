package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private String secret;
    private long expiration;
    private boolean enabled;

    public JwtTokenProvider() {
        this.secret = "VerySecretKeyForJwtDemoApplication123456";
        this.expiration = 3600000; // 1 hour
        this.enabled = true;
    }

    public JwtTokenProvider(String secret,
                            long expiration,
                            boolean enabled) {
        this.secret = secret;
        this.expiration = expiration;
        this.enabled = enabled;
    }

    public String generateToken(Authentication auth,
                                Long userId,
                                String role) {

        if (!enabled) {
            return null;
        }

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> getAllClaimsAsMap(String token) {
        return getAllClaims(token);
    }
}
