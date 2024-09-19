package com.example.demo.team.application.ports;

import com.example.demo.core.infrastructure.persistence.BaseRepository;
import com.example.demo.team.domaine.model.Team;

import java.util.Optional;

public interface TeamRepository extends BaseRepository<Team> {
    Optional<Team> findByPlayerId(String playerId);
}
