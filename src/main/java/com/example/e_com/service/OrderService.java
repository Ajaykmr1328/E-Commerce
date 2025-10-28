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
        BigDecimal total = items.stream()
            .map(i -> i.getUnitPrice().multiply(java.math.BigDecimal.valueOf(i.getQuantity())))
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


