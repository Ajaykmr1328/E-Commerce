package com.example.e_com.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_com.model.user.User;
import com.example.e_com.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getProfile(Principal principal) {
        return userService.findByEmail(principal.getName())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<User> updateProfile(Principal principal, @RequestBody User updates) {
        Long userId = userService.findByEmail(principal.getName()).orElseThrow().getId();
        return ResponseEntity.ok(userService.updateProfile(userId, updates));
    }
}


