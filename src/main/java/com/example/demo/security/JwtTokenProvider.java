package com.example.demo.security;

import com.example.demo.model.User;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "exam-secret-key-123456789";
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private Key key;

    @PostConstruct
    public void init() {
        this.key = new SecretKeySpec(
                SECRET.getBytes(),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("userId", user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
