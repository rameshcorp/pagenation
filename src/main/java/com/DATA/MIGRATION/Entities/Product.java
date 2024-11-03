package com.DATA.MIGRATION.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // Lombok annotation to generate getters, setters, and other methods
@Entity
@Table(name = "product") // Optional: define the table name explicitly
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stockQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // Add the updated_at column
    private LocalDateTime updatedAt; // New field for tracking updates

    // No need to write getters and setters; Lombok generates them.
}
