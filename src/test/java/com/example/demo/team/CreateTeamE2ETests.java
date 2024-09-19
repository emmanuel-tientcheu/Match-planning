package com.example.demo.team;

import com.example.demo.IntegrationTests;
import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.infrastructure.spring.dto.CreateTeamDto;
import org.junit.Assert;
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
public class CreateTeamE2ETests extends IntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TeamRepository repository;

    @Test
    void shouldCreateTest() throws Exception {

        var dto = new CreateTeamDto("real");

        var result = mockMvc.perform(
                MockMvcRequestBuilders.post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", createJwt())
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var team = repository.findById(idResponse.getId()).get();

        Assert.assertEquals(team.getName(), dto.getName());
    }
}
