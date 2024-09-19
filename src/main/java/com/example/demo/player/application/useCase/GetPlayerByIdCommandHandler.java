package com.example.demo.player.application.useCase;

import an.awesome.pipelinr.Command;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommandHandler implements Command.Handler<GetPlayerByIdCommand, PlayerViewModel> {

    private PlayerRepository playerRepository;

    public GetPlayerByIdCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerViewModel handle(GetPlayerByIdCommand getPlayerByIdCommand) {
        var player = playerRepository.findById(getPlayerByIdCommand.getId()).orElseThrow(
                () -> new NotFoundException("Palyer", getPlayerByIdCommand.getId())
        );
        return new PlayerViewModel(player.getName(), player.getId());
    }
}
