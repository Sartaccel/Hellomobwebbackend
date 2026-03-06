package com.hellomobiles.dashboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hellomobiles.dashboard.dto.ProductRequestDTO;
import com.hellomobiles.dashboard.model.Product;
import com.hellomobiles.dashboard.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value="/add", consumes = "multipart/form-data")
    public Product addProduct(
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile image
    ) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ProductRequestDTO dto =
                objectMapper.readValue(productJson, ProductRequestDTO.class);

        return productService.addProduct(dto,image);
    }
}