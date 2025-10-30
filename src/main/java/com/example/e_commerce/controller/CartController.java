package com.example.e_commerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.model.user.User;
import com.example.e_commerce.service.CartService;
import com.example.e_commerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(cartService.getOrCreate(user));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getCartCount(Principal principal) {
        Map<String, Integer> result = new HashMap<>();
        int count = 0;
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                count = cartService.getOrCreate(user).getCartItems().stream()
                        .map(com.example.e_commerce.model.order.CartItem::getQuantity)
                        .filter(Objects::nonNull)
                        .mapToInt(Integer::intValue)
                        .sum();
            }
        }
        result.put("count", count);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(Principal principal, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            User user = userService.findByEmail(principal.getName()).orElseThrow();
            cartService.addItem(user, productId, quantity);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item added to cart successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to add item to cart: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuantity(Principal principal, @PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        try {
            User user = userService.findByEmail(principal.getName()).orElseThrow();
            Integer quantity = payload.get("quantity");
            if (quantity == null || quantity < 1) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid quantity");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Update the cart item quantity
            cartService.updateItemQuantity(user, id, quantity);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Quantity updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update quantity: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(Principal principal, @PathVariable Long id) {
        try {
            User user = userService.findByEmail(principal.getName()).orElseThrow();
            cartService.removeItem(user, id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item removed from cart successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to remove item: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}