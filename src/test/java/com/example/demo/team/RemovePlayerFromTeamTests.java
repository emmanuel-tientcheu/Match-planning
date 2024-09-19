package com.example.demo.team;

import com.example.demo.IntegrationTests;
import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import com.example.demo.team.infrastructure.spring.dto.RemovePlayerFromTeamDto;
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
public class RemovePlayerFromTeamTests extends IntegrationTests {

    Team team;
    Player player;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        playerRepository.clear();
        teamRepository.clear();

        player = new Player("1", "player");
        team = new Team("1", "team");
        team.addMember(player.getId(), Role.TOP);

        playerRepository.save(player);
        teamRepository.save(team);
    }

    @Test
    void ShouldAddPlayerToTeam() throws Exception {
        var dto = new RemovePlayerFromTeamDto(
                team.getId(),
                player.getId()
        );

        var result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/teams/remove-player-to-team")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", createJwt())

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var team = teamRepository.findById(dto.getTeamId()).get();

        Assert.assertNotNull(team);
        Assert.assertFalse(team.hasMember(dto.getPlayerId(), Role.TOP));
    }
}
