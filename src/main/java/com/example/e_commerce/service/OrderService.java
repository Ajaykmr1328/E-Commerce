package com.example.e_commerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.model.order.OrderItem;
import com.example.e_commerce.model.order.OrderStatus;
import com.example.e_commerce.model.user.Address;
import com.example.e_commerce.model.user.User;
import com.example.e_commerce.repository.AddressRepository;
import com.example.e_commerce.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public Order placeOrder(User user, Address shipping, Address billing, List<OrderItem> items) {
        Address savedShipping = addressRepository.save(shipping);
        Address savedBilling = addressRepository.save(billing);
        
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(savedShipping);
        order.setBillingAddress(savedBilling);

        order.setStatus(OrderStatus.PENDING);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem i : items) {
            if (i.getSubtotal() == null) {
                BigDecimal price = i.getPrice();
                BigDecimal qty = BigDecimal.valueOf((long) i.getQuantity());
                i.setSubtotal(price.multiply(qty));
            }
            total = total.add(i.getSubtotal());
        }
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem i : items) {
            i.setOrder(savedOrder);
        }
        savedOrder.setOrderItems(new java.util.HashSet<>(items));

        return orderRepository.save(savedOrder);
    }

    public List<Order> listByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }
    
    public long countAll() {
        return orderRepository.count();
    }
    
    public BigDecimal calculateTotalRevenue() {
        return orderRepository.calculateTotalRevenue();
    }
    
    public Long calculateTotalItemsSold() {
        return orderRepository.calculateTotalItemsSold();
    }
}
