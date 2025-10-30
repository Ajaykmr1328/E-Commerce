package com.example.e_commerce.dto;

import com.example.e_commerce.model.user.Role;
import com.example.e_commerce.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private boolean enabled;
    private String password; 
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }
    
    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPhoneNumber(this.phoneNumber);
        user.setRole(this.role);
        user.setEnabled(this.enabled);
        if (this.password != null && !this.password.trim().isEmpty()) {
            user.setPassword(this.password);
        }
        return user;
    }
}
