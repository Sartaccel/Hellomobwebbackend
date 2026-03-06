package com.hellomobiles.webapplication.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendOtp(String toEmail, String otp) {

        System.out.println("=================================");
        System.out.println("Sending OTP to: " + toEmail);
        System.out.println("OTP: " + otp);
        System.out.println("=================================");
    }
}