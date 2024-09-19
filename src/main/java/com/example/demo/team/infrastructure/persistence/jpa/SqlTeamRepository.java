package com.example.demo.team.infrastructure.persistence.jpa;

import com.example.demo.core.infrastructure.persistence.sql.SqlBaseRepository;
import com.example.demo.team.application.ports.TeamRepository;
import com.example.demo.team.domaine.model.Team;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SqlTeamRepository extends SqlBaseRepository<Team> implements TeamRepository {
    @Override
    public Optional<Team> findByPlayerId(String playerId) {
        var query = entityManager.createQuery(
                "SELECT t FROM Team t JOIN t.members m WHERE m.playerId = :playerId",
                Team.class
        );

        query.setParameter("playerId", playerId);
        return query
                .getResultList()
                .stream()
                .findFirst();
    }

    public SqlTeamRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Team> getEntityClass() {
        return Team.class;
    }
}
