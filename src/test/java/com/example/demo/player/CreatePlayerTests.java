package com.example.demo.player;

import com.example.demo.player.application.useCase.CreatePlayerCommand;
import com.example.demo.player.application.useCase.CreatePlayerCommandHandler;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;


public class CreatePlayerTests {
    @Test
    void shouldCreatePlayer() {
        var repository = new InMemoryPlayerRepository();
        var useCase = new CreatePlayerCommandHandler(repository);

        var command = new CreatePlayerCommand("name");
        var result = useCase.handle(command);

        var expectedPlayer = new Player(result.getId(), "name");
        var actualPlayer = repository.findById(expectedPlayer.getId()).get();

        Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
