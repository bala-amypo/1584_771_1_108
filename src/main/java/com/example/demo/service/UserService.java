package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User save(User user);
    User authenticate(String email, String password);
}
