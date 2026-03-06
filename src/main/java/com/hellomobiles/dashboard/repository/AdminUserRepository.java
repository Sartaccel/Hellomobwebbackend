package com.hellomobiles.dashboard.repository;

import com.hellomobiles.dashboard.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser,Long> {

    Optional<AdminUser> findByEmail(String email);
}