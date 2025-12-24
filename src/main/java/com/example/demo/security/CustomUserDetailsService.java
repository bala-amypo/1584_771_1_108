package com.example.demo.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import java.util.*;

public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, Map<String,Object>> users = new HashMap<>();
    private long seq = 1;

    public Map<String,Object> registerUser(
            String fullName, String email, String password, String role) {

        Map<String,Object> u = new HashMap<>();
        u.put("userId", seq++);
        u.put("email", email);
        u.put("password", password);
        u.put("role", role);
        users.put(email, u);
        return u;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        if (!users.containsKey(email))
            throw new UsernameNotFoundException("User not found");

        return User.withUsername(email)
                .password((String) users.get(email).get("password"))
                .authorities(new ArrayList<>())
                .build();
    }
}
