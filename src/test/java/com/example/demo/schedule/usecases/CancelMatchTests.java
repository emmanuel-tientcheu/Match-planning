package com.example.demo.schedule.usecases;

import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.schedule.application.usecases.CancelMatchCommand;
import com.example.demo.schedule.application.usecases.CancelMatchCommandHandler;
import com.example.demo.schedule.domaine.model.Moment;
import com.example.demo.schedule.domaine.model.ScheduleDay;
import com.example.demo.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CancelMatchTests {
    private InMemoryScheduleDayRepository scheduleDayRepository = new InMemoryScheduleDayRepository();
    private InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    public CancelMatchCommandHandler createHandler() {
        return new CancelMatchCommandHandler(scheduleDayRepository);
    }

    @BeforeEach
    void setUp() {
        teamRepository.clear();
        scheduleDayRepository.clear();
    }

    private Team createTeam(String id) {
        var name = "TEAM " + id;
        var team = new Team(id, name);
        team.addMember(id + "-1", Role.TOP);
        team.addMember(id + "-2", Role.JUNGLE);
        team.addMember(id + "-3", Role.MIDDLE);
        team.addMember(id + "-4", Role.SUPPORT);
        team.addMember(id + "-5", Role.BOTTOM);
        return team;
    }

    @Test
    void shouldCanceledMatch() {
        var scheduleDay = new ScheduleDay("1", LocalDate.parse("2024-01-01"));

        var t1 = createTeam("t1");
        var t2 = createTeam("t2");
        var match = scheduleDay.organize(t1, t2, Moment.MORNING);

        scheduleDayRepository.save(scheduleDay);

        var command = new CancelMatchCommand(match.getId());
        var handler = createHandler();
        handler.handle(command);

        var updateScheduleDay = scheduleDayRepository.findById(scheduleDay.getId()).get();
        Assert.assertFalse(updateScheduleDay.getAt(Moment.MORNING).isPresent());
    }

    @Test
    void whenMatchDoesNotExist_shouldThrow() {

        var command = new CancelMatchCommand("garbage");
        var handler = createHandler();

        Assert.assertThrows(
                NotFoundException.class,
                () -> handler.handle(command)
        );
    }
}
