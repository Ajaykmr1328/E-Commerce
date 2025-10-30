package com.example.e_commerce.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.model.order.Payment;
import com.example.e_commerce.model.order.PaymentStatus;
import com.example.e_commerce.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment process(Order order, String providerRef) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setTransactionId(providerRef);
        payment.setPaymentDate(LocalDateTime.now());
        if (order.getTotalAmount() != null) {
            payment.setAmount(order.getTotalAmount());
        }
        payment.setPaymentMethod("EXTERNAL");
        return paymentRepository.save(payment);
    }
}


