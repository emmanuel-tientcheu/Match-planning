package com.example.demo.auth;

import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.jwtservices.ConcreteJwtService;
import com.example.demo.auth.application.services.passwordHasher.BcryptPasswordHasher;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import com.example.demo.auth.application.usecases.LoginCommand;
import com.example.demo.auth.application.usecases.LoginCommandHandler;
import com.example.demo.auth.domain.User;
import com.example.demo.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.core.domain.exception.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LonginTests {
    private UserRepository userRepository = new InMemoryUserRepository();

    ConcreteJwtService jwtService = new ConcreteJwtService(
            "super_secret_key_please_dont_share",
            60
    );
    private PasswordHasher passwordHasher = new BcryptPasswordHasher();

    private LoginCommandHandler loginCommandHandler() {
        return new LoginCommandHandler(this.userRepository,this.jwtService, this.passwordHasher);
    }
    private User user = new User(
            "123",
            "emmanuel@gmail.com",
            passwordHasher.hash("password")
    );
    @BeforeEach
    void setUser() {
        userRepository.clear();
        userRepository.save(user);
    }

    //Mot de passe et adresse e-mail est correct
    @Nested
    class HappyPath {
        @Test
        void shouldReturnTheUser() {
            var command = new LoginCommand("emmanuel@gmail.com", "password");
            var commandHandler = loginCommandHandler();

            var result = commandHandler.handle(command);

            Assert.assertEquals(result.getId(), user.getId());
            Assert.assertEquals(result.getEmail(), user.getEmail());

            var authenticateUser = jwtService.parse(result.getToken());

            Assert.assertEquals(authenticateUser.getId(), user.getId());
            Assert.assertEquals(authenticateUser.getEmail(), user.getEmail());

        }
    }
    //l'adresse e-mail est incorrect
    @Nested
    class Scenario_TheEmailAdressIsIncorrect {
        @Test
        void shouldThrowNotFound() {

            var command = new LoginCommand("charly@gmail.com", "password");
            var commandHandler =loginCommandHandler();

          Assert.assertThrows(
                  NotFoundException.class,
                  () -> commandHandler.handle(command)
          );

        }
    }

    //le mot de passe est incorrect
    @Nested
    class Scenario_PasswordIsIncorrect {
        @Test
        void shouldThrowNotFound() {

            var command = new LoginCommand(
                    "emmanuel@gmail.com", "not correct password");
            var commandHandler =loginCommandHandler();

            Assert.assertThrows(
                    BadRequestException.class,
                    () -> commandHandler.handle(command)
            );

        }
    }
}
