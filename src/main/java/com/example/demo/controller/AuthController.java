package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User saved = userService.save(user);

        String token = jwtTokenProvider.generateToken(
                saved.getEmail(),
                saved.getRole()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        User user = userService.findByEmail(request.get("email"));

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }
}
