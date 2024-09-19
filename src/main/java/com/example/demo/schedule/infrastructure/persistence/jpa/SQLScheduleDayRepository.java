package com.example.demo.schedule.infrastructure.persistence.jpa;

import com.example.demo.core.infrastructure.persistence.sql.SqlBaseRepository;
import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.domaine.model.ScheduleDay;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

public class SQLScheduleDayRepository extends SqlBaseRepository<ScheduleDay> implements ScheduleDayRepository {
    @Override
    public Optional<ScheduleDay> findByDate(LocalDate date) {
        return entityManager.createQuery(
                "SELECT sd FROM ScheduleDay sd WHERE sd.date = :date", ScheduleDay.class
        )
                .setParameter("date", date)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<ScheduleDay> findByMatchId(String matchId) {
        return entityManager.createQuery(
                "SELECT sd FROM ScheduleDay sd JOIN sd.matches m WHERE m.id =:matchId", ScheduleDay.class
        )
                .setParameter("matchId", matchId)
                .getResultList()
                .stream()
                .findFirst();
    }

    public SQLScheduleDayRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<ScheduleDay> getEntityClass() {
        return ScheduleDay.class;
    }
}
