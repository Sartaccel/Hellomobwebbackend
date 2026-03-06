package com.hellomobiles.dashboard.controller;

import com.hellomobiles.dashboard.dto.LoginRequest;
import com.hellomobiles.dashboard.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/superadmin")
@RequiredArgsConstructor
public class SuperAdminAuthController {

    private final SuperAdminService superAdminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        String token = superAdminService.login(request);

        return ResponseEntity.ok(Map.of(
                "message","Login Successful",
                "token",token
        ));
    }
}