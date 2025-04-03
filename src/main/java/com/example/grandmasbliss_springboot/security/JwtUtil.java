package com.example.grandmasbliss_springboot.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
    private final SecretKey key;

    // ✅ Inject secret key from application.properties
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // ✅ Validate the key after dependency injection
    @PostConstruct
    public void validateKey() {
        if (key == null) {
            throw new IllegalStateException("JWT secret key is not initialized!");
        }
    }

    // ✅ Generate JWT Token
    
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)  // ✅ Specify HS256 algorithm
                .compact();
    }

    // ✅ Extract Username
    public Optional<String> extractUsername(String token) {
        return Optional.ofNullable(parseClaims(token).getSubject());
    }

    // ✅ Validate Token
    public boolean validateToken(String token, String username) {
        Claims claims = parseClaims(token);
        return claims.getSubject().equals(username) && !isTokenExpired(claims);
    }

    // ✅ Check Token Expiration
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // ✅ Parse Claims Properly
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
