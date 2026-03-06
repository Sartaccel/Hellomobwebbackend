package com.hellomobiles.dashboard.dto;

import lombok.Data;

@Data
public class AdminRegisterRequest {

    private String employeeId;
    private String role;
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
    private String confirmPassword;
}