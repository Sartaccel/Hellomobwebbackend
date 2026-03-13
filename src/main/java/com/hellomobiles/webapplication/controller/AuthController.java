package com.hellomobiles.webapplication.controller;

import com.hellomobiles.webapplication.dto.LoginRequest;
import com.hellomobiles.webapplication.dto.RegistrationRequest;
import com.hellomobiles.webapplication.dto.VerifyOtpRequest;
import com.hellomobiles.webapplication.service.LoginService;
import com.hellomobiles.webapplication.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final RegistrationService registrationService;
    private final LoginService loginService;

    public AuthController(RegistrationService registrationService,
                          LoginService loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(registrationService.verifyOtp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@Valid @RequestBody com.hellomobiles.webapplication.dto.GoogleLoginRequest request) {
        return ResponseEntity.ok(loginService.googleLogin(request));
    }
}