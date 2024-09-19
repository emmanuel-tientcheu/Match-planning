package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.team.application.ports.TeamQueries;
import com.example.demo.team.domaine.viewmodel.TeamViewModel;

public class GetTeamByIdCommandHandler implements Command.Handler<GetTeamByIdCommand, TeamViewModel> {

    private final TeamQueries teamQueries;

    public GetTeamByIdCommandHandler(TeamQueries teamQueries) {
        this.teamQueries = teamQueries;
    }

    @Override
    public TeamViewModel handle(GetTeamByIdCommand command) {
        return teamQueries.getTeamById(command.getId());
    }
}
