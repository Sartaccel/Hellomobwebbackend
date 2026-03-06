package com.hellomobiles.webapplication.service;

import com.hellomobiles.webapplication.dto.LoginRequest;
import com.hellomobiles.webapplication.dto.LoginResponse;
import com.hellomobiles.webapplication.entity.User;
import com.hellomobiles.webapplication.repository.UserRepository;
import com.hellomobiles.webapplication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isVerified()) {
            throw new RuntimeException("User not verified");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}
