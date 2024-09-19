package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.team.application.ports.TeamRepository;

public class AddPlayerToTeamCommandHandler implements Command.Handler<AddPlayerToTeamCommand, Void> {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AddPlayerToTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(AddPlayerToTeamCommand command) {
        var team = teamRepository.findById(command.getTeamId()).orElseThrow(
                () -> new NotFoundException("THIS TEAM IS NOT FOUND ", command.getTeamId())
        );

        var player = playerRepository.findById(command.getPlayerId()).orElseThrow(
                () -> new NotFoundException("THIS PLAYER IS NOT FOUND ", command.getPlayerId())
        );

        var teamPlayerBelongsTo = teamRepository.findByPlayerId(player.getId());
        if(teamPlayerBelongsTo.isPresent()) {
            throw new BadRequestException("THIS PLAYER IS ALREADY IN THE TEAM");
        }

        team.addMember(player.getId(), command.getRole());
        teamRepository.save(team);
        return null;
    }
}
