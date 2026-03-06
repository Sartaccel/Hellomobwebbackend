package com.hellomobiles.webapplication.repository;

import com.hellomobiles.webapplication.entity.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailOtpRepository extends JpaRepository<EmailOtp, Long> {
    Optional<EmailOtp> findTopByEmailOrderByIdDesc(String email);
    Optional<EmailOtp> findTopByEmailAndOtpOrderByIdDesc(String email, String otp);
}
