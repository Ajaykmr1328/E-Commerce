package com.example.e_commerce.dto;

import java.math.BigDecimal;

import com.example.e_commerce.model.product.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean active;
    private Long categoryId;
    private String categoryName;

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.stockQuantity = p.getStockQuantity();
        this.active = p.isActive();
        if (p.getCategory() != null) {
            this.categoryId = p.getCategory().getId();
            this.categoryName = p.getCategory().getName();
        }
    }
}
