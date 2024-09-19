package com.example.demo.schedule.usecases;

import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.schedule.application.usecases.OrganizeMatchCommand;
import com.example.demo.schedule.application.usecases.OrganizeMatchCommandHandler;
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

public class OrganizeMatchTests {
    private InMemoryScheduleDayRepository scheduleDayRepository = new InMemoryScheduleDayRepository();
    private InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    public OrganizeMatchCommandHandler createHandler() {
        return new OrganizeMatchCommandHandler(teamRepository, scheduleDayRepository);
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
    void shouldOrganizeMatch() {
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");

        teamRepository.save(t1);
        teamRepository.save(t2);

        var date = LocalDate.parse("2024-01-01");

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
                t1.getId(),
                t2.getId()
        );

        var handler = createHandler();
        var response = handler.handle(command);

        var scheduleDayQuery = scheduleDayRepository.findByDate(date);
        Assert.assertTrue(scheduleDayQuery.isPresent());

        var scheduleDay = scheduleDayQuery.get();

        var matchQuery = scheduleDay.getAt(Moment.MORNING);
        Assert.assertTrue(matchQuery.isPresent());

        var match = matchQuery.get();
        Assert.assertEquals(response.getId(), match.getId());
        Assert.assertTrue(match.includeTeam(t1.getId()));
        Assert.assertTrue(match.includeTeam(t2.getId()));
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");

        teamRepository.save(t1);
        teamRepository.save(t2);

        var date = LocalDate.parse("2024-01-01");

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
              "garbage",
                t2.getId()
        );

        var handler = createHandler();

       Assert.assertThrows(
               NotFoundException.class,
               () -> handler.handle(command)
       );
    }

    @Test
    void whenScheduleAlReadExist() {
        var t1 = createTeam("t1");
        var t2 = createTeam("t2");

        teamRepository.save(t1);
        teamRepository.save(t2);

        var date = LocalDate.parse("2024-01-01");

        var schedule = new ScheduleDay("1", date);
        scheduleDayRepository.save(schedule);

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
                t1.getId(),
                t2.getId()
        );

        var handler = createHandler();
        var response = handler.handle(command);

        var scheduleDayQuery = scheduleDayRepository.findById(schedule.getId());
        Assert.assertTrue(scheduleDayQuery.isPresent());

        var scheduleDay = scheduleDayQuery.get();

        var matchQuery = scheduleDay.getAt(Moment.MORNING);
        Assert.assertTrue(matchQuery.isPresent());

        var match = matchQuery.get();
        Assert.assertEquals(response.getId(), match.getId());
        Assert.assertTrue(match.includeTeam(t1.getId()));
        Assert.assertTrue(match.includeTeam(t2.getId()));
    }

}
