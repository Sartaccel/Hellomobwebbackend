package com.hellomobiles.dashboard.service;

import com.hellomobiles.dashboard.config.AdminJwtUtil;
import com.hellomobiles.dashboard.dto.*;
import com.hellomobiles.dashboard.model.AdminUser;
import com.hellomobiles.dashboard.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminUserRepository repository;
    private final JavaMailSender mailSender;
    private final AdminJwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Generate Random OTP
    private String generateOtp(){
        int otp = (int)(Math.random()*900000) + 100000;
        return String.valueOf(otp);
    }

    // Send OTP Email
    private void sendOtp(String email,String otp){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Admin OTP Verification");
        message.setText("Your OTP is: " + otp + "\nValid for 5 minutes.");

        mailSender.send(message);
    }

    // ===============================
    // Create Admin
    // ===============================
    public String createAdmin(AdminRegisterRequest request){

        if(!request.getPassword().equals(request.getConfirmPassword()))
            throw new RuntimeException("Password mismatch");

        if(repository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        String otp = generateOtp();

        AdminUser admin = AdminUser.builder()
                .employeeId(request.getEmployeeId())
                .role(request.getRole())
                .name(request.getName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(encoder.encode(request.getPassword()))
                .otp(otp)
                .otpExpiryTime(System.currentTimeMillis()+300000)
                .verified(false)
                .build();

        repository.save(admin);

        sendOtp(admin.getEmail(),otp);

        return "Admin created. OTP sent";
    }

    // ===============================
    // Verify OTP
    // ===============================
    public String verifyOtp(VerifyOtpRequest request){

        AdminUser admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if(admin.getOtpExpiryTime() < System.currentTimeMillis())
            throw new RuntimeException("OTP expired");

        if(!admin.getOtp().equals(request.getOtp()))
            throw new RuntimeException("Invalid OTP");

        admin.setVerified(true);
        admin.setOtp(null);

        repository.save(admin);

        return "OTP verified successfully";
    }

    // ===============================
    // Login
    // ===============================
    public Map<String,String> login(AdminLoginRequest request){

        AdminUser admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if(!admin.isVerified())
            throw new RuntimeException("Verify OTP first");

        if(!encoder.matches(request.getPassword(),admin.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateToken(admin.getEmail());

        return Map.of(
                "message","Login successful",
                "token",token
        );
    }

    // ===============================
    // Forgot Password
    // ===============================
    public String forgotPassword(String email){

        AdminUser admin = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        String otp = generateOtp();

        admin.setOtp(otp);
        admin.setOtpExpiryTime(System.currentTimeMillis()+300000);

        repository.save(admin);

        sendOtp(email,otp);

        return "Reset OTP sent";
    }

    // ===============================
    // Verify Reset OTP
    // ===============================
    public String verifyResetOtp(VerifyOtpRequest request){

        AdminUser admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if(admin.getOtpExpiryTime() < System.currentTimeMillis())
            throw new RuntimeException("OTP expired");

        if(!admin.getOtp().equals(request.getOtp()))
            throw new RuntimeException("Invalid OTP");

        return "OTP verified";
    }

    // ===============================
    // Resend OTP
    // ===============================
    public String resendOtp(String email){

        AdminUser admin = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        String otp = generateOtp();

        admin.setOtp(otp);
        admin.setOtpExpiryTime(System.currentTimeMillis()+300000);

        repository.save(admin);

        sendOtp(email,otp);

        return "New OTP sent successfully";
    }

    // ===============================
    // Reset Password
    // ===============================
    public String resetPassword(ResetPasswordRequest request){

        if(!request.getPassword().equals(request.getConfirmPassword()))
            throw new RuntimeException("Password mismatch");

        AdminUser admin = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setPassword(encoder.encode(request.getPassword()));
        admin.setOtp(null);

        repository.save(admin);

        return "Password reset successful";
    }
}