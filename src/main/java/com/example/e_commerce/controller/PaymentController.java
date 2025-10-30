package com.example.e_commerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.model.order.Payment;
import com.example.e_commerce.service.OrderService;
import com.example.e_commerce.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/order/{orderId}")
    public ResponseEntity<Payment> pay(@PathVariable Long orderId) {
        Order order = orderService.get(orderId);
        return ResponseEntity.ok(paymentService.process(order, "REF-" + orderId));
    }
}



