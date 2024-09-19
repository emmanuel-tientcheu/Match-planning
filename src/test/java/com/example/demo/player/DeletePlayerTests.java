package com.example.demo.player;

import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.useCase.DeletePlayerCommand;
import com.example.demo.player.application.useCase.DeletePlayerCommandHandler;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class DeletePlayerTests {
    private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

    private DeletePlayerCommandHandler deleteHandler() {
        return new DeletePlayerCommandHandler(playerRepository);
    }

    @Test
    void shouldDeletePlayer() {
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);
        var commandHandler = deleteHandler();

        commandHandler.handle(new DeletePlayerCommand(existingPlayer.getId()));
        var player = playerRepository.findById(existingPlayer.getId());

        Assert.assertTrue(player.isEmpty());
    }


    @Test
    public void whenPlayerDoesNotExist_shouldFail() {

        var command = new DeletePlayerCommand("garbage");
        var commandHandler = deleteHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals("Player with the key garbage not found", exception.getMessage());


    }
}
