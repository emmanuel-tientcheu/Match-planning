package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.team.application.ports.TeamRepository;

public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Void> {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public RemovePlayerFromTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(RemovePlayerFromTeamCommand command) {

        var team = teamRepository.findById(command.getTeamId()).orElseThrow(
                () -> new NotFoundException("THIS TEAM IS NOT FOUND ", command.getTeamId())
        );

        var player = playerRepository.findById(command.getPlayerId()).orElseThrow(
                () -> new NotFoundException("THIS PLAYER IS NOT FOUND ", command.getPlayerId())
        );

        team.removePLayer(player.getId());
        teamRepository.save(team);
        return null;
    }
}
