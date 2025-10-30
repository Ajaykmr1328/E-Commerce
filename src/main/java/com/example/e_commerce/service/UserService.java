package com.example.e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.e_commerce.model.user.Role;
import com.example.e_commerce.model.user.User;
import com.example.e_commerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user, Role role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateProfile(Long userId, User updates) {
        User existing = userRepository.findById(userId).orElseThrow();
        existing.setFirstName(updates.getFirstName());
        existing.setLastName(updates.getLastName());
        existing.setPhoneNumber(updates.getPhoneNumber());
        return userRepository.save(existing);
    }

    public List<User> list() {
        return userRepository.findAll();
    }
    
    public long countActiveUsers() {
        return userRepository.countByEnabledTrue();
    }
    
    public User create(User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password is required for new users");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }
        return userRepository.save(user);
    }
    
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("User not found with id: " + id));
    }
    
    public User update(Long id, User updates) {
        User existing = userRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("User not found with id: " + id));
        
        existing.setFirstName(updates.getFirstName());
        existing.setLastName(updates.getLastName());
        existing.setEmail(updates.getEmail());
        existing.setPhoneNumber(updates.getPhoneNumber());
        existing.setRole(updates.getRole());
        existing.setEnabled(updates.isEnabled());
        
        if (updates.getPassword() != null && !updates.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(updates.getPassword()));
        }
        
        return userRepository.save(existing);
    }
    
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}


