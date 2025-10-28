package com.example.e_com.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.order.OrderItem;
import com.example.e_com.model.user.User;
import com.example.e_com.service.OrderService;
import com.example.e_com.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public List<Order> myOrders(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return orderService.listByUser(user);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PostMapping
    public ResponseEntity<Order> place(Principal principal, @RequestBody List<OrderItem> items) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        var address = user.getAddresses().stream().findFirst().orElseThrow();
        return ResponseEntity.ok(orderService.placeOrder(user, address, address, items));
    }
}

package com.example.e_com.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_com.model.order.Order;
import com.example.e_com.model.order.OrderItem;
import com.example.e_com.model.user.User;
import com.example.e_com.service.OrderService;
import com.example.e_com.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public List<Order> myOrders(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return orderService.listByUser(user);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PostMapping
    public ResponseEntity<Order> place(Principal principal, @RequestBody List<OrderItem> items) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        // For demo, reuse first address as both shipping and billing
        var address = user.getAddresses().stream().findFirst().orElseThrow();
        return ResponseEntity.ok(orderService.placeOrder(user, address, address, items));
    }
}


