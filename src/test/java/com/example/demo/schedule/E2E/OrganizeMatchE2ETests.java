package com.example.demo.schedule.E2E;

import com.example.demo.IntegrationTests;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.domaine.model.Moment;
import com.example.demo.schedule.infrastructure.spring.dto.OrganizeMatchDto;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.UUID;

public class OrganizeMatchE2ETests extends IntegrationTests {
    Team team = new Team("123", "team");

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScheduleDayRepository scheduleDayRepository;

    @BeforeEach
    public void setUp() {
        teamRepository.clear();
        teamRepository.save(team);
    }

    private Team createTeam(String id) {
        var name = "TEAM " + id;
        var team = new Team(id, name);

        for (Role role : Role.values()) {
            var player = new Player(UUID.randomUUID().toString(), "player");
            playerRepository.save(player);
            team.addMember(player.getId(), role);
        }

        teamRepository.save(team);
        return team;
    }
    @Test
    public void shouldOrganizeMatch() throws Exception {
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");

        var dto = new OrganizeMatchDto(
                "2024-01-01",
                "MORNING",
                t1.getId(),
                t2.getId()
        );

       var response =  mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/schedules/organize-match")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

       var idResponse = objectMapper.readValue(
               response.getResponse().getContentAsString(),
               IdResponse.class
       );

       var scheduleDayQuery = scheduleDayRepository.findByDate(dto.toLocalDate());
        Assert.assertTrue(scheduleDayQuery.isPresent());

        var scheduleDay = scheduleDayQuery.get();

        var matchQuery = scheduleDay.getAt(dto.getMoment());
        Assert.assertTrue(matchQuery.isPresent());

        var match = matchQuery.get();
        Assert.assertEquals(idResponse.getId(), match.getId());
        Assert.assertTrue(match.includeTeam(t1.getId()));
        Assert.assertTrue(match.includeTeam(t2.getId()));
    }

}
