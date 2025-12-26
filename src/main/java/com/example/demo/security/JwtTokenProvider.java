package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import org.springframework.security.core.Authentication;

public class JwtTokenProvider {
    private final String secret;
    private final long validityInMs;
    private final boolean someFlag;
    private final Key key;

    public JwtTokenProvider(String secret, long validityInMs, boolean someFlag) {
        this.secret = secret;
        this.validityInMs = validityInMs;
        this.someFlag = someFlag;
        this.key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                                     SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(Authentication auth, Long userId, String role) {
        String email = auth.getName();
        Date now = new Date();
        Date exp = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Map<String, Object> getAllClaims(String token) {
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        Map<String, Object> map = new HashMap<>(claims);
        map.put("sub", claims.getSubject());
        return map;
    }
}