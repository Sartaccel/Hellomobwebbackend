package com.hellomobiles.dashboard.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description", length = 2000)
    private String description;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "discount_price")
    private Double discountPrice;

    @Column(name = "sale_price")
    private Double salesPrice;

    @Column(name = "tax_included")
    private Boolean taxIncluded;

    @Column(name = "start_date")
    private LocalDateTime expirationStartDate;

    @Column(name = "end_date")
    private LocalDateTime expirationEndDate;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "stock_status")
    private Boolean stockStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private Set<Tag> tags;
}