package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;
import com.example.demo.auth.application.ports.AuthContext;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.ports.PlayerRepository;

public class DeletePlayerCommandHandler implements Command.Handler<DeletePlayerCommand, Void> {
    private PlayerRepository playerRepository;
    private AuthContext authContext;

    public DeletePlayerCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
       // this.authContext = authContext;
    }
    @Override
    public Void handle(DeletePlayerCommand deletePlayerCommand) {
        var player = playerRepository.findById(deletePlayerCommand.getId()).orElseThrow(
                () -> new NotFoundException("Player", deletePlayerCommand.getId())
        );
        playerRepository.delete(player);
        return null;
    }
}
