package com.example.e_com.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.order.Payment;
import com.example.e_com.model.order.PaymentStatus;
import com.example.e_com.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment process(Order order, String providerRef) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentReference(providerRef);
        payment.setPaidAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}


