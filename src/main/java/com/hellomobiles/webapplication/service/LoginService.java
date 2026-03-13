package com.hellomobiles.webapplication.service;

import com.hellomobiles.webapplication.dto.LoginRequest;
import com.hellomobiles.webapplication.entity.User;
import com.hellomobiles.webapplication.repository.UserRepository;
import com.hellomobiles.webapplication.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isVerified()) {
            throw new RuntimeException("User not verified");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public String googleLogin(com.hellomobiles.webapplication.dto.GoogleLoginRequest request) {
        // Here you would typically verify the request.getIdToken() using GoogleIdTokenVerifier
        // For simplicity, we are assuming the token contains the email which you can extract.
        // If integrating google-api-client, you decode it to get email, firstName, lastName.
        
        // This is a placeholder for where the verified email would be obtained:
        // GoogleIdToken.Payload payload = verify(request.getIdToken());
        // String email = payload.getEmail();
        String email = "extracted-email@gmail.com"; // Replace with actual token parsing logic

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setAuthProvider("GOOGLE");
            newUser.setVerified(true); // Google emails are already verified
            // Set other fields from payload or defaults
            // newUser.setFirstName(payload.get("given_name"));
            return userRepository.save(newUser);
        });

        return jwtUtil.generateToken(user.getEmail());
    }
}