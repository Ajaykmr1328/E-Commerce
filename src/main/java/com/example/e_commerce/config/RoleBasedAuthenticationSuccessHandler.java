package com.example.e_commerce.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        String redirectUrl = "/"; 
        
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            
            if ("ROLE_ADMIN".equals(role)) {
                redirectUrl = "/admin/dashboard";
                break;
            } else if ("ROLE_CUSTOMER".equals(role)) {
                redirectUrl = "/";
                break;
            }
        }
        
        response.sendRedirect(redirectUrl);
    }
}
