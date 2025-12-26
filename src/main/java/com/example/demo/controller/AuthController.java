package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 🔓 PUBLIC
    @Operation(security = {})
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        Map<String, Object> result =
                userService.registerUser(
                        user.getFullName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole()
                );

        return ResponseEntity.ok(result);
    }

    // 🔓 PUBLIC
    @Operation(security = {})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        request.get("email"),
                        request.get("password")
                );

        String token = jwtTokenProvider.generateToken(
                auth,
                1L,
                "USER"
        );

        return ResponseEntity.ok(Map.of("token", token));
    }
}
