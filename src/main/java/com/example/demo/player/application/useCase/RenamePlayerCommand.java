package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;

public class RenamePlayerCommand implements Command<Void> {
    private String id;
    private String name;

    public RenamePlayerCommand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
