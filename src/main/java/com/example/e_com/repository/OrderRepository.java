package com.example.e_com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.user.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}


