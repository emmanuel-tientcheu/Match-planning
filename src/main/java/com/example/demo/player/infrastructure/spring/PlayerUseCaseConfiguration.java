package com.example.demo.player.infrastructure.spring;

import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.application.useCase.DeletePlayerCommandHandler;
import com.example.demo.player.application.useCase.GetPlayerByIdCommandHandler;
import com.example.demo.player.application.useCase.RenamePlayerCommandHandler;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import com.example.demo.player.application.useCase.CreatePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class PlayerUseCaseConfiguration {
    @Bean
    public CreatePlayerCommandHandler createPlayerUseCase(PlayerRepository repository) {
        return new CreatePlayerCommandHandler(repository);
    }

    @Bean
    public RenamePlayerCommandHandler renamePlayerUseCase(PlayerRepository repository) {
        return new RenamePlayerCommandHandler(repository);
    }

    @Bean
    public DeletePlayerCommandHandler deletePlayerUseCase(PlayerRepository repository) {
        return new DeletePlayerCommandHandler(repository);
    }

    @Bean
    public GetPlayerByIdCommandHandler getPlayerByIdUseCase(PlayerRepository repository) {
        return new GetPlayerByIdCommandHandler(repository);
    }
}
