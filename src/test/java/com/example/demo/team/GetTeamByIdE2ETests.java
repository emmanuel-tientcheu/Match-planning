package com.example.demo.team;

import com.example.demo.IntegrationTests;
import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import com.example.demo.team.domaine.viewmodel.TeamViewModel;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
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
public class GetTeamByIdE2ETests extends IntegrationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;

    Player player;
    Team team;

    @BeforeEach
    void setUp() {
        player = new Player("1", "player");
        team = new Team("123", "team1");
        team.addMember(player.getId(), Role.JUNGLE);
        playerRepository.save(player);
        teamRepository.save(team);

        System.out.println("Saved Player: " + playerRepository.findById(player.getId()));
        clearDataBaseCache();
    }

    @Test
    void shouldCreateTest() throws Exception {

        var result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/teams/" + team.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var viewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TeamViewModel.class
        );

        Assert.assertEquals(team.getId(), viewModel.getId());
        Assert.assertEquals(team.getName(), viewModel.getName());

        var firstMember = viewModel.getMembers().get(0);
        Assert.assertEquals(player.getId(), firstMember.getPlayerId());
        Assert.assertEquals(player.getName(), firstMember.getPlayerName());
        Assert.assertEquals("JUNGLE", firstMember.getRole());
    }
}
