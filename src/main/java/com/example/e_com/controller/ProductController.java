package com.example.e_com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_com.model.product.Product;
import com.example.e_com.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    @GetMapping
    public List<Product> list() {
        return productService.listActive();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productService.get(id);
    }

    @PostMapping
    public Product create(Product product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, Product update) {
        return productService.update(id, update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam("q") String q) {
        return productService.searchByName(q);
    }

    @GetMapping("/filter/category")
    public List<Product> byCategory(@RequestParam("id") Long categoryId) {
        return productService.filterByCategory(categoryId);
    }

    @GetMapping("/filter/price")
    public List<Product> byPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return productService.filterByPrice(min, max);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> upload(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (!Files.exists(UPLOAD_DIR)) {
            Files.createDirectories(UPLOAD_DIR);
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path destination = UPLOAD_DIR.resolve(id + "-" + filename);
        Files.copy(file.getInputStream(), destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok("Uploaded: " + destination.getFileName());
    }
}

package com.example.e_com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.e_com.model.product.Product;
import com.example.e_com.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Path UPLOAD_DIR = Paths.get("uploads");

    @GetMapping
    public List<Product> list() {
        return productService.listActive();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productService.get(id);
    }

    @PostMapping
    public Product create(Product product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, Product update) {
        return productService.update(id, update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam("q") String q) {
        return productService.searchByName(q);
    }

    @GetMapping("/filter/category")
    public List<Product> byCategory(@RequestParam("id") Long categoryId) {
        return productService.filterByCategory(categoryId);
    }

    @GetMapping("/filter/price")
    public List<Product> byPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return productService.filterByPrice(min, max);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> upload(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        if (!Files.exists(UPLOAD_DIR)) {
            Files.createDirectories(UPLOAD_DIR);
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path destination = UPLOAD_DIR.resolve(id + "-" + filename);
        Files.copy(file.getInputStream(), destination);
        return ResponseEntity.ok("Uploaded: " + destination.getFileName());
    }
}


