package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.player.domain.viewmodel.IdResponse;

public class CreateTeamCommand implements Command<IdResponse> {
    private String name;

    public CreateTeamCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
