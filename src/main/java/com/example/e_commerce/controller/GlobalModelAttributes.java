package com.example.e_commerce.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.e_commerce.model.order.Cart;
import com.example.e_commerce.model.user.User;
import com.example.e_commerce.service.CartService;
import com.example.e_commerce.service.UserService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final UserService userService;
    private final CartService cartService;

    @ModelAttribute
    public void addGlobalAttributes(Model model, Principal principal) {
        int cartItemCount = 0;
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                Cart cart = cartService.getOrCreate(user);
                cartItemCount = cart.getCartItems().stream()
                        .mapToInt(i -> {
                            Integer quantity = i.getQuantity();
                            return quantity == null ? 0 : quantity;
                        })
                        .sum();
            }
        }
        model.addAttribute("cartItemCount", cartItemCount);
    }
}
