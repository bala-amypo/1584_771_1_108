package com.example.demo.security;

import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, User> userMap = new HashMap<>();

    public Map<String, Object> registerUser(String fullName, String email, String password, String role) {
        User user = new User();
        user.setId(1L); // Mock ID
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        
        userMap.put(email, user);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMap.get(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}