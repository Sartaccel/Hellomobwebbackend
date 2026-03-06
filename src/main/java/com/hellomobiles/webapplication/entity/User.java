package com.hellomobiles.webapplication.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private boolean verified;

    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {}

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public boolean isVerified() { return verified; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setVerified(boolean verified) { this.verified = verified; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}