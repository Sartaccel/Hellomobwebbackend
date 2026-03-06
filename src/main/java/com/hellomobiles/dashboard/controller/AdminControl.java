package com.hellomobiles.dashboard.controller;

import com.hellomobiles.dashboard.dto.*;
import com.hellomobiles.dashboard.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminControl {

    private final AdminService service;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody AdminRegisterRequest request){
        return ResponseEntity.ok(service.createAdmin(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request){
        return ResponseEntity.ok(service.verifyOtp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody EmailRequest request){
        return ResponseEntity.ok(service.forgotPassword(request.getEmail()));
    }

    @PostMapping("/verify-reset-otp")
    public ResponseEntity<?> verifyResetOtp(@RequestBody VerifyOtpRequest request){
        return ResponseEntity.ok(service.verifyResetOtp(request));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody EmailRequest request){
        return ResponseEntity.ok(service.resendOtp(request.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        return ResponseEntity.ok(service.resetPassword(request));
    }
}