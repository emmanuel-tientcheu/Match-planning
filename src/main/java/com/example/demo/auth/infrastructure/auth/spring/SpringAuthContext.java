package com.example.demo.auth.infrastructure.auth.spring;

import com.example.demo.auth.application.ports.AuthContext;
import com.example.demo.auth.domain.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringAuthContext implements AuthContext {
    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .isAuthenticated();
    }

    @Override
    public Optional<AuthUser> getUser() {
        return Optional.ofNullable(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        )
                .map(auth -> {
                    if (auth.getPrincipal() instanceof AuthUser) {
                        return (AuthUser) auth.getPrincipal();
                    }
                    return null;
                });
    }
}
