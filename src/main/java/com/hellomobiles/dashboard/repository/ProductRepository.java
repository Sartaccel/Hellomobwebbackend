package com.hellomobiles.dashboard.repository;

import com.hellomobiles.dashboard.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}