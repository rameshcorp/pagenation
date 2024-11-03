package com.DATA.MIGRATION.Dto;

import com.DATA.MIGRATION.Entities.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stockQuantity;
    private String message; // Field to hold a message

    // Constructor for setting only the message
    public ProductResponseDto(String message) {
        this.message = message;
    }

    // Default constructor
    public ProductResponseDto() {
        // Default constructor
    }

    // Converts a Product entity to ProductResponseDto
    public static ProductResponseDto fromProduct(Product product, String message) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setDescription(product.getDescription());
        responseDto.setCategory(product.getCategory());
        responseDto.setPrice(product.getPrice());
        responseDto.setStockQuantity(product.getStockQuantity());
        responseDto.setMessage(message);
        return responseDto;
    }
}
