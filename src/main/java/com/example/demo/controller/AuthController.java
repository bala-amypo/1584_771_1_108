package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
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
    public User register(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {

        String email = req.get("email");
        String password = req.get("password");

        return userService.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .map(u -> {
                    String token = jwtTokenProvider.generateToken(
                            new org.springframework.security.authentication
                                    .UsernamePasswordAuthenticationToken(
                                            u.getEmail(), null),
                            u.getId(),
                            u.getRole());
                    return ResponseEntity.ok(token);
                })
                .orElse(ResponseEntity.status(401).build());
    }
}
