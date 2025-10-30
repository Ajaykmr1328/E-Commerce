package com.example.e_commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce.model.product.Category;
import com.example.e_commerce.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category update) {
        Category existing = categoryRepository.findById(id).orElseThrow();
        existing.setName(update.getName());
        existing.setDescription(update.getDescription());
        return categoryRepository.save(existing);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category get(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }
}


