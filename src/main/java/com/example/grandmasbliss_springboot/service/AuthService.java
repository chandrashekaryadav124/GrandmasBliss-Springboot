package com.example.grandmasbliss_springboot.service;

import com.example.grandmasbliss_springboot.model.User;
import com.example.grandmasbliss_springboot.repository.UserRepository;
import com.example.grandmasbliss_springboot.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // **Register a new user**
    public String registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "User already exists!";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return "User registered successfully!";
    }

    // **Authenticate user and return JWT token**
    public String authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Check if password matches (if passwords were hashed)
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(user.getUsername());
            }
        }
        throw new RuntimeException("Invalid email or password!");
    }
}
