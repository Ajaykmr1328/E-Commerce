package com.example.e_com.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.order.OrderItem;
import com.example.e_com.model.order.OrderStatus;
import com.example.e_com.model.user.Address;
import com.example.e_com.model.user.User;
import com.example.e_com.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order placeOrder(User user, Address shipping, Address billing, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shipping);
        order.setBillingAddress(billing);
        order.setOrderItems(new java.util.HashSet<>(items));
        // compute subtotal per item if not provided
        for (OrderItem i : items) {
            if (i.getSubtotal() == null) {
                java.math.BigDecimal price = i.getPrice();
                java.math.BigDecimal qty = java.math.BigDecimal.valueOf(i.getQuantity());
                i.setSubtotal(price.multiply(qty));
            }
            i.setOrder(order);
        }
        BigDecimal total = items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public List<Order> listByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }
}


