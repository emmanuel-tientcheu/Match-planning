package com.example.demo.auth.application.services.passwordHasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHasher implements PasswordHasher{
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public Boolean match(String clearPassword, String hashedPassword) {
        return encoder.matches(clearPassword, hashedPassword);
    }

}
