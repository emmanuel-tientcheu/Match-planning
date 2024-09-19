package com.example.demo.player.application.useCase;
import an.awesome.pipelinr.Command;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {
    private final PlayerRepository repository;

    public CreatePlayerCommandHandler(PlayerRepository repository) {
        this.repository = repository;
    }
    public IdResponse execute(String name) {
        var player = new Player(UUID.randomUUID().toString(), name);
        repository.save(player);

        return new IdResponse(player.getId());
    }

    @Override
    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(UUID.randomUUID().toString(), command.getName());
        repository.save(player);

        return new IdResponse(player.getId());
    }
}
