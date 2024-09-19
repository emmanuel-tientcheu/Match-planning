package com.example.demo.player;

import com.example.demo.player.application.useCase.GetPlayerByIdCommand;
import com.example.demo.player.application.useCase.GetPlayerByIdCommandHandler;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class GetPlayerByIdTests {
    @Test
    void ShouldGetPLayerById() {
        var repository = new InMemoryPlayerRepository();
        var player = new Player("123", "player");

        repository.save(player);
        var command = new GetPlayerByIdCommandHandler(repository);
        var result = command.handle(new GetPlayerByIdCommand(player.getId()));

        Assert.assertEquals(player.getId(), result.getId());
    }
}
