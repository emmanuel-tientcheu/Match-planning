package com.example.demo.auth.application.services.passwordHasher;

public interface PasswordHasher {
    String hash(String password);
    Boolean match(String clearPassword, String hashedPassword);
}
