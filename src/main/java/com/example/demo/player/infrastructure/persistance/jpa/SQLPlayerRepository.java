package com.example.demo.player.infrastructure.persistance.jpa;

import com.example.demo.core.infrastructure.persistence.sql.SqlBaseRepository;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.UUID;

public class SQLPlayerRepository extends SqlBaseRepository<Player> implements PlayerRepository {

    public SQLPlayerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Player> getEntityClass() {
        return Player.class;
    }
}
