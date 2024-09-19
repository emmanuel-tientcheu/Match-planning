package com.example.demo.auth;

import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import com.example.demo.auth.domain.User;
import com.example.demo.auth.domain.viewModel.LoggedUserViewModel;
import com.example.demo.auth.infrastructure.spring.CreateUserDTO;
import com.example.demo.auth.infrastructure.spring.LoginDTO;
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

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
public class LoginE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    @BeforeEach
    public void setup() {

        userRepository.clear();
        var user = new User(
                "123",
                "emmanuel@gmail.com",
                passwordHasher.hash("password")
        );
        userRepository.save(user);
    }
    @Test
    public void ShouldLoginUser() throws Exception {

        var dto = new LoginDTO(
                "emmanuel@gmail.com",
                "password"
        );

        var result =  mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        LoggedUserViewModel user = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                LoggedUserViewModel.class
        );

        Assert.assertEquals("123", user.getId());
        Assert.assertEquals("emmanuel@gmail.com", user.getEmail());
    }

    @Test
    public void whenPasswordIsUnvailable_shouldThrow() throws Exception {

        var dto = new CreateUserDTO(
                "emmanuel@gmail.com",
                "123"
        );

        mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
}
