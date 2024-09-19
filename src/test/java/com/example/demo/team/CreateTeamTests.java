package com.example.demo.team;

import com.example.demo.team.application.usecases.CreateTeamCommand;
import com.example.demo.team.application.usecases.CreateTeamCommandHandler;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreateTeamTests {
    InMemoryTeamRepository inMemoryTeamRepository = new InMemoryTeamRepository();
    CreateTeamCommandHandler commandHandler() {
        return new CreateTeamCommandHandler(inMemoryTeamRepository);
    }

    @Test
    void shouldCreateTeam() {

        var commandHandler = commandHandler();
        var command = new CreateTeamCommand("real");

        var result = commandHandler.handle(command);
        var team = inMemoryTeamRepository.findById(result.getId()).get();

        Assert.assertEquals(command.getName(), team.getName());
    }
}
