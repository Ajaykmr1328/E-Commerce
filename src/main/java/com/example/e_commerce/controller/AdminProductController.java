package com.example.e_commerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dto.ProductDTO;
import com.example.e_commerce.model.product.Category;
import com.example.e_commerce.model.product.Product;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        List<ProductDTO> dto = productService.list().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(new ProductDTO(productService.get(id)));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStockQuantity(dto.getStockQuantity());
        p.setActive(dto.isActive());
        if (dto.getCategoryId() != null) {
            Category c = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
            p.setCategory(c);
        }
        Product saved = productService.create(p);
        return ResponseEntity.ok(new ProductDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product update = new Product();
        update.setName(dto.getName());
        update.setDescription(dto.getDescription());
        update.setPrice(dto.getPrice());
        update.setStockQuantity(dto.getStockQuantity());
        update.setActive(dto.isActive());
        if (dto.getCategoryId() != null) {
            Category c = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
            update.setCategory(c);
        } else {
            update.setCategory(null);
        }
        Product saved = productService.update(id, update);
        return ResponseEntity.ok(new ProductDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
