package com.example.demo.auth.infrastructure.spring;

import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.jwtservices.JwtService;
import com.example.demo.auth.application.services.passwordHasher.BcryptPasswordHasher;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import com.example.demo.auth.application.usecases.CreateUserCommandeHandler;
import com.example.demo.auth.application.usecases.LoginCommandHandler;
import com.example.demo.player.application.ports.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfiguration {
    @Bean
    public CreateUserCommandeHandler createUserCommandeHandler (
            UserRepository UserRepository,
            PasswordHasher passwordHasher
    ) {
        return new CreateUserCommandeHandler(UserRepository, passwordHasher);
    }

    @Bean
    public LoginCommandHandler loginCommandHandler (
            UserRepository UserRepository,
            JwtService jwtService,
            PasswordHasher passwordHasher
    ) {
        return new LoginCommandHandler(
                UserRepository,
                jwtService,
                passwordHasher
        );
    }
}
