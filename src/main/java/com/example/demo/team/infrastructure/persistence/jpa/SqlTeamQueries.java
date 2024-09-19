package com.example.demo.team.infrastructure.persistence.jpa;

import com.example.demo.team.application.ports.TeamQueries;
import com.example.demo.team.domaine.model.Team;
import com.example.demo.team.domaine.viewmodel.TeamViewModel;
import jakarta.persistence.EntityManager;

public class SqlTeamQueries implements TeamQueries {
    private final EntityManager entityManager;

    public SqlTeamQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TeamViewModel getTeamById(String id) {
        var query = entityManager.createQuery(
                "SELECT DISTINCT t FROM Team t " +
                        "JOIN FETCH t.members m " +
                        "JOIN FETCH m.player " +
                        "WHERE t.id = :id", Team.class
        );

        query.setParameter("id", id);
        var result = query.getSingleResult();
        var players = result.getMembers().stream()
                .map(member -> {
                    return new TeamViewModel.TeamMember(
                            member.getId(),
                            member.getPlayer().getId(),
                            member.getPlayer().getName(),
                            member.getRole().name()
                    );
                }).toList();

        return new TeamViewModel(
                result.getId(),
                result.getName(),
                players
        );
    }
}
