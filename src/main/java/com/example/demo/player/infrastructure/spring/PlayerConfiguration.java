package com.example.demo.player.infrastructure.spring;

import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.infrastructure.persistance.jpa.SQLPlayerDataAccesor;
import com.example.demo.player.infrastructure.persistance.jpa.SQLPlayerRepository;
import com.example.demo.player.infrastructure.persistance.ram.InMemoryPlayerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfiguration {
    @Bean
  public PlayerRepository playerRepository(EntityManager entityManager) {
        return new SQLPlayerRepository(entityManager);
    }
}
