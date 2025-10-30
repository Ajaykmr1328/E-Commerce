package com.example.e_commerce.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dto.OrderItemResponse;
import com.example.e_commerce.dto.OrderResponse;
import com.example.e_commerce.model.order.Order;
import com.example.e_commerce.model.order.OrderItem;
import com.example.e_commerce.model.user.Address;
import com.example.e_commerce.model.user.User;
import com.example.e_commerce.service.CartService;
import com.example.e_commerce.service.OrderService;
import com.example.e_commerce.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping
    public List<OrderResponse> myOrders(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        return orderService.listByUser(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subtotal(item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable Long id) {
        return convertToResponse(orderService.get(id));
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
            Principal principal,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String pinCode,
            @RequestParam String paymentMethod) {
        try {
            User user = userService.findByEmail(principal.getName()).orElseThrow();

            var cart = cartService.getOrCreate(user);
            if (cart.getCartItems().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Your cart is empty");
                return ResponseEntity.badRequest().body(response);
            }

            Address shippingAddress = new Address();
            shippingAddress.setUser(user);
            shippingAddress.setStreetAddress(address);
            shippingAddress.setCity(""); 
            shippingAddress.setState("");
            shippingAddress.setCountry("");
            shippingAddress.setZipCode(pinCode);
            shippingAddress.setAddressType("SHIPPING");
            shippingAddress.setDefault(false);
            
            Address billingAddress = new Address();
            billingAddress.setUser(user);
            billingAddress.setStreetAddress(address);
            billingAddress.setCity("");
            billingAddress.setState("");
            billingAddress.setCountry("");
            billingAddress.setZipCode(pinCode);
            billingAddress.setAddressType("BILLING");
            billingAddress.setDefault(false);

            var orderItems = cart.getCartItems().stream()
                    .map(cartItem -> {
                        OrderItem item = new OrderItem();
                        item.setProduct(cartItem.getProduct());
                        item.setQuantity(cartItem.getQuantity());
                        item.setPrice(cartItem.getPrice());
                        return item;
                    })
                    .toList();

            Order order = orderService.placeOrder(user, shippingAddress, billingAddress, orderItems);
            
            cartService.clear(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order placed successfully");
            response.put("orderId", order.getId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to place order: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<OrderResponse> place(Principal principal, @RequestBody List<OrderItem> items) {
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        var address = user.getAddresses().stream().findFirst().orElseThrow();
        Order order = orderService.placeOrder(user, address, address, items);
        return ResponseEntity.ok(convertToResponse(order));
    }
}
