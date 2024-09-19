package com.example.demo.team.usecases;

import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import com.example.demo.team.application.usecases.AddPlayerToTeamCommand;
import com.example.demo.team.application.usecases.AddPlayerToTeamCommandHandler;
import com.example.demo.team.application.usecases.RemovePlayerFromTeamCommand;
import com.example.demo.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemovePlayerFromTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();
    InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

    Player player = new Player("1", "player");
    Team team = new Team("123", "team1");

    private RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler() {
        return new RemovePlayerFromTeamCommandHandler(teamRepository, playerRepository);
    }

    @BeforeEach
    void setUp() {
        teamRepository.clear();
        playerRepository.clear();
        team.addMember(player.getId(), Role.JUNGLE);
        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    void shouldRemovePlayerFromTeam() {

        var commandHandler = removePlayerFromTeamCommandHandler();
        var command = new RemovePlayerFromTeamCommand(team.getId(), player.getId());
        commandHandler.handle(command);

        var team = teamRepository.findById(command.getTeamId()).get();

        Assert.assertFalse(
                team.hasMember(command.getPlayerId(), Role.JUNGLE)
        );
    }
}
