package com.example.demo.auth;


import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.passwordHasher.BcryptPasswordHasher;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import com.example.demo.auth.application.usecases.CreateUserCommandeHandler;
import com.example.demo.auth.application.usecases.CreateUserCommand;
import com.example.demo.auth.domain.User;
import com.example.demo.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import com.example.demo.auth.infrastructure.spring.CreateUserDTO;
import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.player.domain.viewmodel.IdResponse;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class auth {

    private InMemoryUserRepository repository = new InMemoryUserRepository();
    private PasswordHasher passwordHasher = new BcryptPasswordHasher();

    public CreateUserCommandeHandler createUserCommandeHandler() {
        return new CreateUserCommandeHandler(repository, passwordHasher);
    }

    @BeforeEach
    public void setup() {
        repository.clear();
    }

    @Test
    public void shouldRegister() {
        User actualUser = null;

        var command = new CreateUserCommand(
                "emmanuel@gmail.com",
                "password"
        );

        var createUserHandler = createUserCommandeHandler();
        var response = createUserHandler.handle(command);

        actualUser = repository.findById(response.getId()).get();

        Assert.assertEquals("emmanuel@gmail.com", actualUser.getEmail());
        Assert.assertTrue(
                passwordHasher.match(command.getPassword(), actualUser.getPassword())
        );
    }

    @Test
    public void ShouldCreateUserWithSameEmail() {
        var existingUser = new User(
                "123",
                "emmanuel@gmail.com",
                "password"
        );
        repository.save(existingUser);

        var command = new CreateUserCommand(
                "emmanuel@gmail.com",
                "password"
        );

        var createUserHandler = createUserCommandeHandler();

        var exception = Assert.assertThrows(
                //IllegalArgumentException.class,
                BadRequestException.class,
                () -> createUserHandler.handle(command)
        );

    }
}