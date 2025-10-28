package com.example.e_com.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_com.model.user.User;
import com.example.e_com.service.CartService;
import com.example.e_com.service.UserService;

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

    @PostMapping("/add")
    public ResponseEntity<?> add(Principal principal, @RequestParam Long productId, @RequestParam int quantity) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(cartService.addItem(user, productId, quantity));
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> remove(Principal principal, @PathVariable Long id) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return ResponseEntity.ok(cartService.removeItem(user, id));
    }
}


