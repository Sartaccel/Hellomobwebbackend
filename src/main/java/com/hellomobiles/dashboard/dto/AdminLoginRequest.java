package com.hellomobiles.dashboard.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {

    private String email;
    private String password;
}