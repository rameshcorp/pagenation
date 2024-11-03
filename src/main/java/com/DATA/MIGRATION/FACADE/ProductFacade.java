package com.DATA.MIGRATION.FACADE;

import com.DATA.MIGRATION.Dto.ProductRequestDto;
import com.DATA.MIGRATION.Dto.ProductResponseDto;
import com.DATA.MIGRATION.Entities.Product;
import com.DATA.MIGRATION.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProductFacade {

    private final ProductDao productDao;

    @Autowired
    public ProductFacade(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productRequestDto.toProduct();
        Product savedProduct = productDao.save(product);
        return ProductResponseDto.fromProduct(savedProduct, "Product inserted successfully");
    }
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        if (id == null || id <= 0) {
            return new ProductResponseDto("Invalid ID: Only positive numeric values are allowed.");
        }

        try {
            // Fetch the existing product by ID
            Product product = productDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Update fields from the DTO if they’re not null
            if (productRequestDto.getName() != null) {
                product.setName(productRequestDto.getName());
            }
            if (productRequestDto.getDescription() != null) {
                product.setDescription(productRequestDto.getDescription());
            }
            if (productRequestDto.getCategory() != null) {
                product.setCategory(productRequestDto.getCategory());
            }
            if (productRequestDto.getPrice() != null) {
                product.setPrice(productRequestDto.getPrice());
            }
            if (productRequestDto.getStockQuantity() != null) {
                product.setStockQuantity(productRequestDto.getStockQuantity());
            }

            // Set the updated time
            product.setUpdatedAt(LocalDateTime.now());

            // Save the updated product
            Product updatedProduct = productDao.save(product);
            return ProductResponseDto.fromProduct(updatedProduct, "Product updated successfully");

        } catch (RuntimeException e) {
            return new ProductResponseDto("Invalid ID: Product not found");
        }
    }



    public ProductResponseDto deleteProduct(Long id) {
        if (!productDao.existsById(id)) {
            return new ProductResponseDto("Product not found");
        }
        productDao.deleteById(id);
        return new ProductResponseDto("Product deleted successfully");
    }
    public ProductResponseDto getProductById(String id) {
        try {
            // Try to convert the string to a Long
            Long numericId = Long.valueOf(id);

            // Fetch the product by numeric ID
            Product product = productDao.findById(numericId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            return ProductResponseDto.fromProduct(product, "Product retrieved successfully");
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be parsed to a Long
            throw new IllegalArgumentException("Please enter a numeric value for the ID.");
        }
    }


    public Page<ProductResponseDto> searchProducts(String name, String category, Pageable pageable) {
        // Update the query to include pagination and sorting
        List<Product> products = productDao.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(name, category, pageable);
        int totalCount = productDao.countByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(name, category);
        return new PageImpl<>(products.stream()
                .map(product -> ProductResponseDto.fromProduct(product, "Product found"))
                .toList(), pageable, totalCount);
    }

}
