package com.example.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_com.model.order.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}


