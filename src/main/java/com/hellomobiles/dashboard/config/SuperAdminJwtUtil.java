package com.hellomobiles.dashboard.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class SuperAdminJwtUtil {

    private static final String SECRET =
            "hellomobilesSuperAdminJwtSecretKey123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION = 1000 * 60 * 60;

    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}