package com.DATA.MIGRATION.Dto;

import com.DATA.MIGRATION.Entities.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private Long id; // Ensure this field is included
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stockQuantity;

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.id); // Set id if needed
        product.setName(this.name);
        product.setDescription(this.description);
        product.setCategory(this.category);
        product.setPrice(this.price);
        product.setStockQuantity(this.stockQuantity);
        return product;
    }
}

