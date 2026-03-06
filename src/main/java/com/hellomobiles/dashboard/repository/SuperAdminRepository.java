package com.hellomobiles.dashboard.repository;

import com.hellomobiles.dashboard.model.Role;
import com.hellomobiles.dashboard.model.SuperAdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdminUser, Long> {

    Optional<SuperAdminUser> findByUsernameAndRole(String username, Role role);
}