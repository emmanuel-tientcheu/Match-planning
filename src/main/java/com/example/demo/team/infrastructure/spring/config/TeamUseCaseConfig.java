package com.example.demo.team.infrastructure.spring.config;

import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.team.application.ports.TeamQueries;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.application.usecases.AddPlayerToTeamCommandHandler;
import com.example.demo.team.application.usecases.CreateTeamCommandHandler;
import com.example.demo.team.application.usecases.GetTeamByIdCommandHandler;
import com.example.demo.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamUseCaseConfig {

    @Bean
    CreateTeamCommandHandler createTeamCommandHandler(
            TeamRepository repository
    ) {
        return  new CreateTeamCommandHandler(repository);
    }

    @Bean
    AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler(
            PlayerRepository playerRepository,
            TeamRepository teamRepository){
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler(
            PlayerRepository playerRepository,
            TeamRepository teamRepository){
        return new RemovePlayerFromTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    GetTeamByIdCommandHandler getTeamByIdCommandHandler(TeamQueries teamQueries) {
        return new GetTeamByIdCommandHandler(teamQueries);
    }
}
