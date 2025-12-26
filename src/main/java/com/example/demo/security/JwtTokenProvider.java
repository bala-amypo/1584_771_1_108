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

    private final String SECRET =
            "VerySecretKeyForJwtDemoApplication123456";

    private final long EXPIRATION = 3600000; 

    public String generateToken(Authentication auth,
                                Long userId,
                                String role) {

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET) 
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
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> getAllClaimsAsMap(String token) {
        return getAllClaims(token);
    }
}
