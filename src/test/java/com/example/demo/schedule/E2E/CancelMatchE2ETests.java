package com.example.demo.schedule.E2E;

import com.example.demo.IntegrationTests;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.domaine.model.Moment;
import com.example.demo.schedule.domaine.model.ScheduleDay;
import com.example.demo.schedule.infrastructure.spring.dto.CancelMatchDto;
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

import java.time.LocalDate;
import java.util.UUID;

public class CancelMatchE2ETests extends IntegrationTests {
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
    void shouldCancelMatch() throws Exception{
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");

        var scheduleDay = new ScheduleDay("1", LocalDate.now());
        var match = scheduleDay.organize(t1, t2, Moment.MORNING);
        scheduleDayRepository.save(scheduleDay);

        var dto = new CancelMatchDto(
               match.getId()
        );

        var response =  mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/schedules/cancel-match")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var updateScheduleDay = scheduleDayRepository.findById(scheduleDay.getId()).get();
        Assert.assertFalse(updateScheduleDay.getAt(Moment.MORNING).isPresent());
    }
}
