package com.example.e_commerce.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.e_commerce.model.order.Cart;
import com.example.e_commerce.model.user.User;
import com.example.e_commerce.service.CartService;
import com.example.e_commerce.service.CategoryService;
import com.example.e_commerce.service.OrderService;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("products", productService.listActive());
        return "home";
    }

    @GetMapping("/products")
    public String products(
        Model model,
        @org.springframework.web.bind.annotation.RequestParam(value = "q", required = false) String q,
        @org.springframework.web.bind.annotation.RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        model.addAttribute("categories", categoryService.list());
        if (q != null && !q.isBlank()) {
            model.addAttribute("products", productService.searchByName(q));
        } else if (categoryId != null) {
            model.addAttribute("products", productService.filterByCategory(categoryId));
        } else {
            model.addAttribute("products", productService.listActive());
        }
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("product", productService.get(id));
        return "product-detail";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("products", productService.listActive());
        
        model.addAttribute("totalRevenue", orderService.calculateTotalRevenue());
        model.addAttribute("totalItemsSold", orderService.calculateTotalItemsSold());
        model.addAttribute("totalOrders", orderService.countAll());
        model.addAttribute("activeUsers", userService.countActiveUsers());
        
        return "admin";
    }

    @GetMapping("/admin/products")
    public String adminProducts(Model model) {
        model.addAttribute("categories", categoryService.list());
        model.addAttribute("products", productService.list());
        return "admin-products";
    }

    @GetMapping("/admin/categories")
    public String adminCategories(Model model) {
        model.addAttribute("categories", categoryService.list());
        return "admin-categories";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.list());
        return "categories";
    }

    @GetMapping("/admin/orders")
    public String adminOrders(Model model) {
        model.addAttribute("orders", orderService.listAll());
        return "admin-orders";
    }

    @GetMapping("/admin/orders/{id}")
    public String adminOrderDetails(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.get(id));
        return "admin-order-detail";
    }

    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", userService.list());
        return "admin-users";
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                Cart cart = cartService.getOrCreate(user);
                model.addAttribute("cartItems", cart.getCartItems());
                model.addAttribute("cartTotal", cart.getTotalAmount());
            } else {
                model.addAttribute("cartItems", null);
                model.addAttribute("cartTotal", BigDecimal.ZERO);
            }
        } else {
            model.addAttribute("cartItems", null);
            model.addAttribute("cartTotal", BigDecimal.ZERO);
        }
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                Cart cart = cartService.getOrCreate(user);
                model.addAttribute("cartItems", cart.getCartItems());
                model.addAttribute("cartTotal", cart.getTotalAmount());
                model.addAttribute("user", user);
            }
        }
        return "checkout";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        log.info("Profile page accessed. Principal: {}", principal != null ? principal.getName() : "null");

        if (principal != null) {
            log.info("Looking up user with email: {}", principal.getName());
            User user = userService.findByEmail(principal.getName()).orElse(null);

            if (user != null) {
                log.info("User found: {} {} ({})", user.getFirstName(), user.getLastName(), user.getRole());
                model.addAttribute("user", user);
            } else {
                log.warn("User not found for email: {}", principal.getName());
            }
        } else {
            log.warn("Principal is null - user not authenticated");
        }
        return "profile";
    }

    @PostMapping("/api/user/profile/update")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String phoneNumber,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        log.info("Updating profile for user: {}", principal.getName());
        
        try {
            User user = userService.findByEmail(principal.getName()).orElseThrow();
            User updates = new User();
            updates.setFirstName(firstName);
            updates.setLastName(lastName);
            updates.setPhoneNumber(phoneNumber);
            
            userService.updateProfile(user.getId(), updates);
            
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            log.info("Profile updated successfully for user: {}", principal.getName());
        } catch (Exception e) {
            log.error("Error updating profile: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        
        return "redirect:/profile";
    }

    @GetMapping("/orders")
    public String orderHistory(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                model.addAttribute("orders", orderService.listByUser(user));
            }
        }
        return "order-history";
    }

    @GetMapping("/orders/{id}")
    public String orderTracking(@PathVariable Long id, Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                try {
                    model.addAttribute("order", orderService.get(id));
                } catch (Exception e) {
                    return "redirect:/orders";
                }
            }
        }
        return "order-tracking";
    }

    @GetMapping("/order-confirmation")
    public String orderConfirmation(Model model) {
        return "order-confirmation";
    }
}
