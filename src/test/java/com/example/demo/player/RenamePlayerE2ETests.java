package com.example.demo.player;

import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.player.infrastructure.spring.CreatePlayerDTO;
import com.example.demo.player.infrastructure.spring.RenamePlayerDTO;
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
public class RenamePlayerE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void ShouldRenamePlayer() throws Exception {
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        var dto = new RenamePlayerDTO("new name");

        var result =  mockMvc
                .perform(MockMvcRequestBuilders.patch(
                        "/players/" + existingPlayer.getId() + "/name"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        var player = playerRepository.findById(existingPlayer.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(dto.getName(), player.getName());

    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        var dto = new RenamePlayerDTO("new name");

        mockMvc
                .perform(MockMvcRequestBuilders.patch(
                                "/players/" + "garbage" + "/name"
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
