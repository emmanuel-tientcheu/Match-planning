package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.domaine.model.Team;

import java.util.UUID;

public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse>{
    private final TeamRepository repository;

    public CreateTeamCommandHandler(TeamRepository repository) {
        this.repository = repository;
    }
    @Override
    public IdResponse handle(CreateTeamCommand createTeamCommand) {
        var team = new Team(
                UUID.randomUUID().toString(),
                createTeamCommand.getName()
        );
        repository.save(team);
        return new IdResponse(team.getId());
    }
}
