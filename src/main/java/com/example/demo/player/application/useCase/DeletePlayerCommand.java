package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;

public class DeletePlayerCommand implements Command<Void> {
    private String id;

    public DeletePlayerCommand() {}

    public DeletePlayerCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
