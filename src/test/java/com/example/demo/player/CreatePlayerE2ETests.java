package com.example.demo.player;


import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import com.example.demo.player.infrastructure.spring.CreatePlayerDTO;
import jakarta.transaction.Transactional;
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
@Transactional
public class CreatePlayerE2ETests {
   @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void SouldCreateUser() throws Exception {
      var dto = new CreatePlayerDTO("player");

      var result =  mockMvc
                .perform(MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

      var idResponse = objectMapper.readValue(
              result.getResponse().getContentAsString(),
              IdResponse.class
      );

      var player = playerRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(dto.getName(), player.getName());
    }
}
