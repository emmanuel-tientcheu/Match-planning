package com.example.demo.auth.infrastructure.spring;

import com.example.demo.auth.application.services.jwtservices.ConcreteJwtService;
import com.example.demo.auth.application.services.jwtservices.JwtService;
import com.example.demo.auth.application.services.passwordHasher.BcryptPasswordHasher;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public PasswordHasher passwordHasher () {
        return new BcryptPasswordHasher();
    }

    @Bean
    public JwtService jwtService() {
        return new ConcreteJwtService(
                "secret_password_please_dont_share",
                3600
        );
    }
}
