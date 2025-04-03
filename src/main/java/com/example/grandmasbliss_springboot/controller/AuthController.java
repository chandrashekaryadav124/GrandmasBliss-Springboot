package com.example.grandmasbliss_springboot.controller;

import com.example.grandmasbliss_springboot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        String response = authService.registerUser(
                userData.get("username"),
                userData.get("email"),
                userData.get("password")
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String token = authService.authenticateUser(loginData.get("email"), loginData.get("password"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            // Log the exception (optional)
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
