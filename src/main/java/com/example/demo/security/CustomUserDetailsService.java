// src/main/java/com/example/demo/security/CustomUserDetailsService.java
package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class CustomUserDetailsService implements UserDetailsService {
    private final Map<String, Map<String, Object>> users = new HashMap<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    public CustomUserDetailsService() {}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Map<String, Object> u = users.get(email);
        if (u == null) throw new UsernameNotFoundException("User not found");
        String password = (String) u.get("password");
        return User.withUsername(email).password(password).authorities(new ArrayList<>()).build();
    }

    public Map<String, Object> registerUser(String fullName, String email, String password, String role) {
        long id = idSeq.getAndIncrement();
        Map<String, Object> u = new HashMap<>();
        u.put("userId", id);
        u.put("fullName", fullName);
        u.put("email", email);
        u.put("password", password);
        u.put("role", role);
        users.put(email, u);
        return u;
    }
}