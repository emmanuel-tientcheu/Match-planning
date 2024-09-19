package com.example.demo.team.infrastructure.spring.config;

import com.example.demo.team.application.ports.TeamQueries;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.infrastructure.persistence.jpa.SqlTeamQueries;
import com.example.demo.team.infrastructure.persistence.jpa.SqlTeamRepository;
import com.example.demo.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamAdapterConfig {
    @Bean
    public TeamRepository teamRepository(EntityManager entityManager) {
        return new SqlTeamRepository(entityManager);
    }
    @Bean
    public TeamQueries teamQueries(EntityManager entityManager) {
        return new SqlTeamQueries(entityManager);
    }
}
