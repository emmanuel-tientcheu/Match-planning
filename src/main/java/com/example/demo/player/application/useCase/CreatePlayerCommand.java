package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;
import com.example.demo.player.domain.viewmodel.IdResponse;

public class CreatePlayerCommand implements Command<IdResponse> {
    private String name;

    public CreatePlayerCommand(String name) { this.name = name; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }
}
