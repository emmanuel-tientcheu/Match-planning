package com.example.demo.auth;

import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.domain.User;
import com.example.demo.auth.infrastructure.spring.CreateUserDTO;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.player.infrastructure.spring.CreatePlayerDTO;
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
public class AuthRegisterE2ETests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.clear();
    }
    @Test
    public void ShouldRegisterUser() throws Exception {
        var dto = new CreateUserDTO(
                "emmanuel@gmail.com",
                "password"
        );

        var result =  mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var user = userRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(user);
        Assert.assertEquals(dto.getEmail(), user.getEmail());
    }

    @Test
    public void whenEmailAdressIsUnvailable_shouldThrow() throws Exception {
        var user = new User(
                "123",
                "emmanuel@gmail.com",
                "password"
        );

        userRepository.save(user);

        var dto = new CreateUserDTO(
                "emmanuel@gmail.com",
                "password"
        );

        var result =  mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
}
