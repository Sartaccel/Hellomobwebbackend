package com.hellomobiles.webapplication.service;

import com.hellomobiles.webapplication.dto.RegistrationRequest;
import com.hellomobiles.webapplication.dto.VerifyOtpRequest;
import com.hellomobiles.webapplication.entity.EmailOtp;
import com.hellomobiles.webapplication.entity.User;
import com.hellomobiles.webapplication.repository.EmailOtpRepository;
import com.hellomobiles.webapplication.repository.UserRepository;
import com.hellomobiles.webapplication.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailOtpRepository emailOtpRepository;

    @Autowired
    private EmailService emailService;

    public String register(RegistrationRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setVerified(false);
        userRepository.save(user);

        String otp = OtpUtil.generateOtp();

        EmailOtp emailOtp = new EmailOtp();
        emailOtp.setEmail(request.getEmail());
        emailOtp.setOtp(otp);
        emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        emailOtp.setUsed(false);
        emailOtpRepository.save(emailOtp);

        emailService.sendOtp(request.getEmail(), otp);

        return "OTP sent successfully";
    }

    public String verifyOtp(VerifyOtpRequest request) {

        EmailOtp emailOtp = emailOtpRepository
                .findTopByEmailOrderByIdDesc(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (emailOtp.isUsed()) {
            throw new RuntimeException("OTP already used");
        }

        if (!emailOtp.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (emailOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        emailOtp.setUsed(true);
        emailOtpRepository.save(emailOtp);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setVerified(true);
        userRepository.save(user);

        return "User verified successfully";
    }
}