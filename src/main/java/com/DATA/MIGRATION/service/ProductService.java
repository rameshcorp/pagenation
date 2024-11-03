package com.DATA.MIGRATION.service;


import com.DATA.MIGRATION.Dto.ProductRequestDto;
import com.DATA.MIGRATION.Dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);
    ProductResponseDto deleteProduct(Long id);
    ProductResponseDto getProductById(Long id);
    Page<ProductResponseDto> searchProducts(String name, String category, Pageable pageable);
}
