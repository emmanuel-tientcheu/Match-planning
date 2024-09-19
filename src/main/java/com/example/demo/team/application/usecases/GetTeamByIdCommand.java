package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.team.domaine.viewmodel.TeamViewModel;

public class GetTeamByIdCommand implements Command<TeamViewModel> {

    private String id;

    public GetTeamByIdCommand(){}
    public GetTeamByIdCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
