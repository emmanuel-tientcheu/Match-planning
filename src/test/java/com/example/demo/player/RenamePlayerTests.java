package com.example.demo.player;

import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.application.useCase.RenamePlayerCommand;
import com.example.demo.player.application.useCase.RenamePlayerCommandHandler;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RenamePlayerTests {
    private final InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

    private RenamePlayerCommandHandler createHandler() {
        return new RenamePlayerCommandHandler(playerRepository);
    }
    @Test
    void shoudRenamePlayer() {

        var player = new Player("123", "old name");
        playerRepository.save(player);

        var command = new RenamePlayerCommand("123", "new name");
        var commandHandler = createHandler();
        commandHandler.handle(command);

        Player actualPlayer = playerRepository.findById(player.getId()).get();
        Assert.assertEquals(command.getName(), actualPlayer.getName());

    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() {

        var command = new RenamePlayerCommand("garbage", "new name");
        var commandHandler = createHandler();

        var exception = Assert.assertThrows(
            NotFoundException.class,
            () -> commandHandler.handle(command)
    );
       Assert.assertEquals("Player with the key garbage not found", exception.getMessage());


    }
}
