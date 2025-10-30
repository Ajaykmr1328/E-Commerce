package com.example.e_commerce.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.e_commerce.model.order.Cart;
import com.example.e_commerce.model.order.CartItem;
import com.example.e_commerce.model.product.Product;
import com.example.e_commerce.model.user.User;
import com.example.e_commerce.repository.CartRepository;
import com.example.e_commerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart getOrCreate(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            c.setTotalAmount(BigDecimal.ZERO);
            return cartRepository.save(c);
        });
    }

    public Cart addItem(User user, Long productId, int quantity) {
        Cart cart = getOrCreate(user);
        Product product = productRepository.findById(productId).orElseThrow();
        
        if (cart.getCartItems() == null) {
            cart.setCartItems(new java.util.HashSet<>());
        }
        
        CartItem existingItem = cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            cart.getCartItems().add(newItem);
        }
        
        recalc(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItem(User user, Long cartItemId) {
        Cart cart = getOrCreate(user);
        cart.getCartItems().removeIf(i -> cartItemId.equals(i.getId()));
        recalc(cart);
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(User user, Long cartItemId, int quantity) {
        Cart cart = getOrCreate(user);
        CartItem item = cart.getCartItems().stream()
            .filter(i -> cartItemId.equals(i.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        item.setQuantity(quantity);
        recalc(cart);
        return cartRepository.save(cart);
    }

    public void clear(User user) {
        Cart cart = getOrCreate(user);
        cart.getCartItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private void recalc(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
            .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}


