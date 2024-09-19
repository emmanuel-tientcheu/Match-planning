package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;
import com.example.demo.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommand implements Command<PlayerViewModel> {
    private final String id;

    public GetPlayerByIdCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
