package com.example.demo.schedule.application.ports;

import com.example.demo.core.infrastructure.persistence.BaseRepository;
import com.example.demo.schedule.domaine.model.ScheduleDay;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduleDayRepository extends BaseRepository<ScheduleDay> {
    Optional<ScheduleDay> findByDate(LocalDate date);
    Optional<ScheduleDay> findByMatchId(String matchId);
}
