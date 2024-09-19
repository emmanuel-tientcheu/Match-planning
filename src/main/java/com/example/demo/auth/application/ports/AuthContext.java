package com.example.demo.auth.application.ports;

import com.example.demo.auth.domain.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();
    Optional<AuthUser> getUser();
}
