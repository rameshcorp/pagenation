package com.DATA.MIGRATION.service;

import com.DATA.MIGRATION.Dto.ProductRequestDto;
import com.DATA.MIGRATION.Dto.ProductResponseDto;
import com.DATA.MIGRATION.FACADE.ProductFacade; // Import your ProductFacade
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductFacade productFacade;

    @Autowired
    public ProductServiceImpl(ProductFacade productFacade) {
        this.productFacade = productFacade; // Use facade
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        return productFacade.createProduct(productRequestDto);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        return productFacade.updateProduct(id, productRequestDto);
    }

    @Override
    public ProductResponseDto deleteProduct(Long id) {
        return productFacade.deleteProduct(id);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        return productFacade.getProductById(String.valueOf(id));
    }

    @Override
    public Page<ProductResponseDto> searchProducts(String name, String category, Pageable pageable) {
        return productFacade.searchProducts(name, category, pageable);
    }
}
