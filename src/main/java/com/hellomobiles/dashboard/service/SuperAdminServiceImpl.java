package com.hellomobiles.dashboard.service;

import com.hellomobiles.dashboard.config.SuperAdminJwtUtil;
import com.hellomobiles.dashboard.dto.LoginRequest;
import com.hellomobiles.dashboard.model.Role;
import com.hellomobiles.dashboard.model.SuperAdminUser;
import com.hellomobiles.dashboard.repository.SuperAdminRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository userRepository;
    private final SuperAdminJwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    // Credentials stored in code
    private final String USERNAME = "superadmin";
    private final String PASSWORD = "123456";

    // Runs when application starts
    @PostConstruct
    public void createSuperAdmin(){

        if(userRepository.findByUsernameAndRole(USERNAME, Role.SUPER_ADMIN).isEmpty()){

            SuperAdminUser user = SuperAdminUser.builder()
                    .username(USERNAME)
                    .password(passwordEncoder.encode(PASSWORD))
                    .role(Role.SUPER_ADMIN)
                    .build();

            userRepository.save(user);

            System.out.println("Super Admin created successfully");
        }
    }

    @Override
    public String login(LoginRequest request) {

        SuperAdminUser user = userRepository
                .findByUsernameAndRole(request.getUsername(), Role.SUPER_ADMIN)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
}