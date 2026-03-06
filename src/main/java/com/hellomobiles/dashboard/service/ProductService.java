package com.hellomobiles.dashboard.service;

import com.hellomobiles.dashboard.dto.ProductRequestDTO;
import com.hellomobiles.dashboard.model.*;
import com.hellomobiles.dashboard.repository.ProductRepository;
import com.hellomobiles.dashboard.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final S3Service s3Service;

    public Product addProduct(ProductRequestDTO dto, MultipartFile image){

        String imageUrl = s3Service.uploadFile(image);

        Set<Tag> tagList = dto.getTags().stream().map(tagName -> {

            Tag tag = tagRepository.findByTagName(tagName);

            if(tag == null){
                tag = new Tag();
                tag.setTagName(tagName);
                tagRepository.save(tag);
            }

            return tag;

        }).collect(Collectors.toSet());

        Double salesPrice = dto.getProductPrice() - dto.getDiscountPrice();

        Product product = Product.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .productPrice(dto.getProductPrice())
                .discountPrice(dto.getDiscountPrice())
                .salesPrice(salesPrice)
                .taxIncluded(dto.getTaxIncluded())
                .expirationStartDate(dto.getExpirationStartDate())
                .expirationEndDate(dto.getExpirationEndDate())
                .stockQuantity(dto.getStockQuantity())
                .stockStatus(dto.getStockStatus())
                .imageUrl(imageUrl)
                .category(Category.valueOf(dto.getCategory().toUpperCase()))
                .tags(tagList)
                .build();

        return productRepository.save(product);
    }
}