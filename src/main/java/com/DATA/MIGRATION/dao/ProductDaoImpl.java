package com.DATA.MIGRATION.dao;

import com.DATA.MIGRATION.Entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Product save(Product product) {
        if (product.getId() != null && existsById(product.getId())) {
            // Update existing product
            String updateSql = "UPDATE product SET name = ?, description = ?, category = ?, price = ?, stock_quantity = ?, updated_at = ? WHERE id = ?";

            // Set the current date and time for the update
            product.setUpdatedAt(LocalDateTime.now());

            // Execute the update statement
            jdbcTemplate.update(updateSql,
                    product.getName(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getUpdatedAt(),
                    product.getId());

        } else {
            // Insert new product
            String insertSql = "INSERT INTO product (name, description, category, price, stock_quantity, created_at) VALUES (?, ?, ?, ?, ?, ?)";

            // Set the current date and time for creation
            product.setCreatedAt(LocalDateTime.now());

            // Execute the insert statement
            jdbcTemplate.update(insertSql,
                    product.getName(),
                    product.getDescription(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getCreatedAt());

            // Retrieve the last inserted ID (works for MySQL)
            String lastIdSql = "SELECT LAST_INSERT_ID()";
            Long generatedId = jdbcTemplate.queryForObject(lastIdSql, Long.class);

            // Set the generated ID back to the product
            product.setId(generatedId);
        }
        return product;
    }


    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, this::mapRowToProduct).stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM product WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }
    @Override
    public List<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category, Pageable pageable) {
        // Prepare the base query
        StringBuilder sql = new StringBuilder("SELECT * FROM product");
        List<Object> params = new ArrayList<>();

        boolean hasCondition = false;

        // Add conditions based on whether name and category are provided
        if (name != null && !name.isEmpty()) {
            sql.append(" WHERE LOWER(name) LIKE LOWER(?)");
            params.add("%" + name + "%");
            hasCondition = true;
        }

        if (category != null && !category.isEmpty()) {
            if (hasCondition) {
                sql.append(" OR");
            } else {
                sql.append(" WHERE");
            }
            sql.append(" LOWER(category) LIKE LOWER(?)");
            params.add("%" + category + "%");
        }

        // Append sorting and pagination
        sql.append(" ORDER BY ").append(pageable.getSort().toString().replace(":", " ").replace(";", ""));
        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        // Execute the query
        return jdbcTemplate.query(sql.toString(), params.toArray(), this::mapRowToProduct);
    }



    @Override
    public int countByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category) {
        String countSql = "SELECT COUNT(*) FROM product WHERE LOWER(name) LIKE LOWER(?) OR LOWER(category) LIKE LOWER(?)";
        return jdbcTemplate.queryForObject(countSql, new Object[]{"%" + name + "%", "%" + category + "%"}, Integer.class);
    }


    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id")); // Adjust field names according to your table schema
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setCategory(rs.getString("category"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        return product;
    }
}
