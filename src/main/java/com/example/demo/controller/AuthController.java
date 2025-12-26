// src/main/java/com/example/demo/controller/AuthController.java
package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Operation(summary = "Register user")
    public User register(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login and get JWT")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) throw new RuntimeException("Invalid credentials");
        User u = opt.get();
        if (!passwordEncoder.matches(request.getPassword(), u.getPassword()))
            throw new RuntimeException("Invalid credentials");

        Authentication auth = new UsernamePasswordAuthenticationToken(u.getEmail(), request.getPassword());
        String token = jwtTokenProvider.generateToken(auth, u.getId(), u.getRole());

        AuthResponse resp = new AuthResponse();
        resp.setToken(token);
        resp.setUserId(u.getId());
        resp.setEmail(u.getEmail());
        resp.setRole(u.getRole());
        return resp;
    }
}