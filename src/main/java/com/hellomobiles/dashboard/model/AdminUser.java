package com.hellomobiles.dashboard.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="admin_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String role;
    private String name;

    @Column(unique = true)
    private String email;

    private String mobileNumber;
    private String password;

    private String otp;
    private Long otpExpiryTime;

    private boolean verified;
}