package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;

import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.ports.PlayerRepository;

public class RenamePlayerCommandHandler implements Command.Handler<RenamePlayerCommand, Void> {
    private final PlayerRepository repository;

    public RenamePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }
    @Override
    public Void handle(RenamePlayerCommand renamePlayerCommand) {
        var playerQuery = repository.findById(renamePlayerCommand.getId());
        System.out.println(playerQuery);
        if (playerQuery.isEmpty()) {
            throw new NotFoundException("Player", renamePlayerCommand.getId());
        }

        var player = playerQuery.get();
        player.rename(renamePlayerCommand.getName());
        repository.save(player);

        return null;
    }
}
