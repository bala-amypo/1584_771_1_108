package com.example.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, Map<String, Object>> users = new HashMap<>();
    private long idSequence = 1;

    public Map<String, Object> registerUser(
            String fullName,
            String email,
            String password,
            String role) {

        Map<String, Object> user = new HashMap<>();
        user.put("userId", idSequence++);
        user.put("email", email);
        user.put("password", password);
        user.put("role", role);

        users.put(email, user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        if (!users.containsKey(email)) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.withUsername(email)
                .password((String) users.get(email).get("password"))
                .authorities(new ArrayList<>())
                .build();
    }
}
