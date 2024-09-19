package com.example.demo.schedule.infrastructure.persistence.ram;

import com.example.demo.core.infrastructure.persistence.ram.InMemoryBaseEntity;
import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.domaine.model.ScheduleDay;

import java.time.LocalDate;
import java.util.Optional;

public class InMemoryScheduleDayRepository extends InMemoryBaseEntity<ScheduleDay> implements ScheduleDayRepository {
    @Override
    public Optional<ScheduleDay> findByDate(LocalDate date) {
        return entities.values().stream().filter(scheduleDay -> scheduleDay.getDate().equals(date))
                .findFirst();
    }

    @Override
    public Optional<ScheduleDay> findByMatchId(String matchId) {
        return entities.values().stream()
                .filter(scheduleDay -> scheduleDay.containsMatch(matchId))
                .findFirst();
    }
}
