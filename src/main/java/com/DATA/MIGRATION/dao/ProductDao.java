package com.DATA.MIGRATION.dao;

import com.DATA.MIGRATION.Entities.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product save(Product product);
    Optional<Product> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);

    // Remove the duplicate method and ensure to use the correct Pageable
    List<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category, Pageable pageable);

    int countByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);


}
