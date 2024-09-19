package com.example.demo.team.usecases;

import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import com.example.demo.team.application.usecases.AddPlayerToTeamCommand;
import com.example.demo.team.application.usecases.AddPlayerToTeamCommandHandler;
import com.example.demo.team.domaine.model.Role;
import com.example.demo.team.domaine.model.Team;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddPlayerToTeam {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();
    InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();
    Player player;
    Team team;

    private AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler() {
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @BeforeEach
    void setUp() {
        teamRepository.clear();
        playerRepository.clear();
        team = new Team("123", "team1");
        player = new Player("1", "player");
        playerRepository.save(player);
        teamRepository.save(team);
    }
    @Test
    void shouldAddPlayerToTeam() {

        var commandHandler = addPlayerToTeamCommandHandler();
        var command = new AddPlayerToTeamCommand(team.getId(), player.getId(), Role.TOP);
        commandHandler.handle(command);

        var team = teamRepository.findById(command.getTeamId()).get();

        Assert.assertTrue(team.hasMember(command.getPlayerId(), command.getRole()));
    }

    @Test
    void whenPlayerDoesNotExist_ShouldFaild() {

        var commandHandler = addPlayerToTeamCommandHandler();
        var command = new AddPlayerToTeamCommand(team.getId(), "garbage", Role.TOP);


       var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );

       Assert.assertEquals(
               "THIS PLAYER IS NOT FOUND  with the key " + command.getPlayerId() + " not found",
               exception.getMessage()
       );
    }

    @Test
    void whenTeamDoesNotExist_ShouldFaild() {

        var commandHandler = addPlayerToTeamCommandHandler();
        var command = new AddPlayerToTeamCommand("garbage", player.getId(), Role.TOP);


        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );

        Assert.assertEquals(
                "THIS TEAM IS NOT FOUND  with the key " + command.getTeamId() + " not found",
                exception.getMessage()
        );
    }

    @Test
    void whenPlayerAreAlreadyToAnotherTeam() {
        Team team2 = new Team("12", "team2");
        team2.addMember(player.getId(), Role.TOP);
        teamRepository.save(team2);

        var commandHandler = addPlayerToTeamCommandHandler();
        var command = new AddPlayerToTeamCommand(team.getId(), player.getId(), Role.TOP);

        Assert.assertThrows(
                BadRequestException.class,
                () -> commandHandler.handle(command)
        );
    }
}
