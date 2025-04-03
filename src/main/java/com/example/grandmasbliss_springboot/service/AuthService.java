package com.example.grandmasbliss_springboot.service;


import com.example.grandmasbliss_springboot.model.User;
import com.example.grandmasbliss_springboot.repository.UserRepository;
import com.example.grandmasbliss_springboot.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already registered!");
        }

        User user = new User(null, username, email, passwordEncoder.encode(password), Collections.singleton("CUSTOMER"));
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(user.getUsername());
            }
        }
        throw new RuntimeException("Invalid email or password!");
    }
}
