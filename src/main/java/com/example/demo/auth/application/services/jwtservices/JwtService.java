package com.example.demo.auth.application.services.jwtservices;

import com.example.demo.auth.domain.AuthUser;
import com.example.demo.auth.domain.User;

public interface JwtService {
    String tokenize(User user);
    AuthUser parse(String token);
}
