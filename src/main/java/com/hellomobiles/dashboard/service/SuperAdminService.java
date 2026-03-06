package com.hellomobiles.dashboard.service;

import com.hellomobiles.dashboard.dto.LoginRequest;

public interface SuperAdminService {

    String login(LoginRequest request);
}