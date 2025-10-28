package com.example.e_com.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_com.model.product.Category;
import com.example.e_com.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByCategoryAndActiveTrue(Category category);
    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    List<Product> findByPriceBetweenAndActiveTrue(BigDecimal min, BigDecimal max);
}


