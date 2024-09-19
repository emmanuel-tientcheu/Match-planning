package com.example.demo.team.infrastructure.persistence.ram;

import com.example.demo.core.infrastructure.persistence.ram.InMemoryBaseEntity;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.domaine.model.Team;

import java.util.Optional;

public class InMemoryTeamRepository extends InMemoryBaseEntity<Team> implements TeamRepository {
    @Override
    public Optional<Team> findByPlayerId(String playerId) {

      return entities.values().stream()
                .filter(team -> team.getMembers().stream().anyMatch(teamMember -> teamMember.getPlayerId().equals(playerId)))
              .findFirst();

    }
}
