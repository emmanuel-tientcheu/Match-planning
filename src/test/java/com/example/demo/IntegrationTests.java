package com.example.demo;

import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.jwtservices.JwtService;
import com.example.demo.auth.domain.User;
import com.example.demo.player.application.ports.PlayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
public class IntegrationTests {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PlayerRepository playerRepository;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtService jwtservice;

    @Autowired
    protected EntityManager entityManager;

    protected String createJwt() {
        var user = userRepository.findByEmail("emmanuel@gmail.com").orElse(null);

        if (user== null) {
             user = new User("123", "emmanuel@gmail.com", "password");
        }
      //  userRepository.save(user);
        var jwt = "Bearer " + jwtservice.tokenize(user);
        return jwt;
    }

    protected void clearDataBaseCache() {
        entityManager.flush();
        entityManager.clear();
    }
}
