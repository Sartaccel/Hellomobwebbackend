package com.hellomobiles.dashboard.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductRequestDTO {

    private String productName;
    private String description;

    private Double productPrice;
    private Double discountPrice;

    private Boolean taxIncluded;

    private LocalDateTime expirationStartDate;
    private LocalDateTime expirationEndDate;

    private Integer stockQuantity;
    private Boolean stockStatus;

    private String category;

    private List<String> tags;
}