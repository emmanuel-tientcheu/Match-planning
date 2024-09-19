package com.example.demo.schedule.domaine.model;

import com.example.demo.core.domain.model.BaseEntity;
import com.example.demo.team.domaine.model.Team;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "schedule_days")
public class ScheduleDay extends BaseEntity<ScheduleDay> {
    @Column(name = "date")
    private LocalDate date;

    @OneToMany(
            mappedBy = "scheduleDay",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Moment, Match> matches;

    public ScheduleDay() {}

    public ScheduleDay(String id, LocalDate date){
        super(id);
        this.date = date;
        matches = new EnumMap<>(Moment.class);
    }
    public ScheduleDay(String id, LocalDate date, Map<Moment, Match> matches){
        super(id);
        this.date = date;
        this.matches = matches;
    }
    public Match organize(Team t1, Team t2, Moment moment) {
        if(matches.containsKey(moment)) {
            throw new IllegalStateException("Moment " + moment + "is already taken");
        }

        var teamAlreadyPlay = matches.values().stream()
                .anyMatch(match -> match.includeTeam(t1.getId()) || match.includeTeam(t2.getId()));
        if(teamAlreadyPlay) {
            throw new IllegalStateException("One of the team is already playing");
        }

        if(!t1.isInComplete() || !t2.isInComplete()) {
            throw new IllegalStateException("One of the team is incomplete");
        }

        var match = new Match(
                UUID.randomUUID().toString(),
                this.getId(),
                t1.getId(),
                t2.getId()
        );
        matches.put(moment, match);
        return match;
    }

    public void cancel(String matchId) {
       var moment = matches.keySet().stream()
               .filter(m -> matches.get(m).getId().equals(matchId))
               .findFirst();
       moment.ifPresent(matches::remove);
    }

    public Optional<Match> getAt(Moment moment) {
       return matches.containsKey(moment) ?
                Optional.of(matches.get(moment)) :
                Optional.empty();
    }

    public boolean containsMatch(String matchId) {
        return this.matches.values().stream().anyMatch(match -> match.getId().equals(matchId));
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public ScheduleDay deepClone() {
        return new ScheduleDay(
                getId(),
                this.date,
                this.matches.keySet().stream()
                        .collect(
                                () -> new EnumMap<>(Moment.class),
                                (map, moment) -> map.put(moment, matches.get(moment).deepClone()),
                                Map::putAll
                        )
        );
    }



}
