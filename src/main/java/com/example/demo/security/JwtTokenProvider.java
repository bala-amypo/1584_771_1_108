package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component   // ✅ THIS IS THE FIX
public class JwtTokenProvider {

    private final String jwtSecret;
    private final long jwtExpirationInMs;
    private final boolean includeEmailClaim;

    public JwtTokenProvider() {
        // default values for Spring Boot runtime
        this.jwtSecret = "VerySecretKeyForJwtDemoApplication123456";
        this.jwtExpirationInMs = 3600000L; // 1 hour
        this.includeEmailClaim = true;
    }

    public JwtTokenProvider(String jwtSecret, long jwtExpirationInMs, boolean includeEmailClaim) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.includeEmailClaim = includeEmailClaim;
    }

    public String generateToken(Authentication authentication, Long userId, String role) {

        String email = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Map<String, Object> getAllClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
