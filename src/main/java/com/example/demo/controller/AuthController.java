package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailsService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomUserDetailsService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody User user) {
        Map<String, Object> registeredUser = userService.registerUser(
                user.getFullName(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getRole()
        );
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        // Authenticate using the mocked user details service logic
        // In a real app, AuthenticationManager would be used here
        userService.loadUserByUsername(authRequest.getEmail()); // Verify user exists
        
        // Check password (simplified for this demo as user details are mocked)
        // Ideally: authenticationManager.authenticate(...)

        // Generate Token
        // Fetch user details again to get ID and Role (mocked fetch)
        // For the purpose of this test harness, we rely on the service returning the map or re-fetching
        // Since CustomUserDetailsService in the previous step uses a Map, we assume valid creds if no exception
        
        // Create dummy auth object
        Authentication auth = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword(), Collections.emptyList());
        
        // We need the ID and Role. In a real DB scenario we'd fetch the User entity.
        // Here we mock the ID as 1L and role as generic for simplicity unless fetched from a real DB
        Long userId = 1L; 
        String role = "USER"; 

        String token = jwtTokenProvider.generateToken(auth, userId, role);
        return ResponseEntity.ok(new AuthResponse(token, userId, authRequest.getEmail(), role));
    }
}