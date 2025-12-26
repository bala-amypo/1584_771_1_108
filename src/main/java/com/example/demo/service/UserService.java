package com.example.demo.service;

import java.util.Map;

public interface UserService {

    // REQUIRED by AuthController
    Map<String, Object> register(
            String fullName,
            String email,
            String password,
            String role
    );
}
