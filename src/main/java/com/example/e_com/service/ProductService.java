package com.example.e_com.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_com.model.product.Category;
import com.example.e_com.model.product.Product;
import com.example.e_com.repository.CategoryRepository;
import com.example.e_com.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product update) {
        Product existing = productRepository.findById(id).orElseThrow();
        existing.setName(update.getName());
        existing.setDescription(update.getDescription());
        existing.setPrice(update.getPrice());
        existing.setStockQuantity(update.getStockQuantity());
        existing.setCategory(update.getCategory());
        existing.setActive(update.isActive());
        return productRepository.save(existing);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public List<Product> listActive() {
        return productRepository.findByActiveTrue();
    }

    public List<Product> searchByName(String q) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(q);
    }

    public List<Product> filterByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return productRepository.findByCategoryAndActiveTrue(category);
    }

    public List<Product> filterByPrice(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetweenAndActiveTrue(min, max);
    }
}


