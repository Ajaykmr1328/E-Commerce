package com.example.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.order.Payment;
import com.example.e_com.service.OrderService;
import com.example.e_com.service.PaymentService;

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

package com.example.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.order.Payment;
import com.example.e_com.service.OrderService;
import com.example.e_com.service.PaymentService;

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


