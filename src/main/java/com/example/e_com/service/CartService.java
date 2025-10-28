package com.example.e_com.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.e_com.model.order.Cart;
import com.example.e_com.model.order.CartItem;
import com.example.e_com.model.product.Product;
import com.example.e_com.model.user.User;
import com.example.e_com.repository.CartRepository;
import com.example.e_com.repository.ProductRepository;

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
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());
        cart.getCartItems().add(item);
        recalc(cart);
        return cartRepository.save(cart);
    }

    public Cart removeItem(User user, Long cartItemId) {
        Cart cart = getOrCreate(user);
        cart.getCartItems().removeIf(i -> cartItemId.equals(i.getId()));
        recalc(cart);
        return cartRepository.save(cart);
    }

    private void recalc(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
            .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}


