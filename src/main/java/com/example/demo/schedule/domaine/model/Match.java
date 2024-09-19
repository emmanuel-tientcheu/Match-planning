package com.example.demo.schedule.domaine.model;

import com.example.demo.core.domain.model.BaseEntity;
import com.example.demo.team.domaine.model.Team;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match extends BaseEntity<Match> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_id")
    @MapsId("firstId")
    private Team first;

    @Column(name = "first_id")
    private String firstId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_id")
    @MapsId("secondId")
    private Team second;

    @Column(name = "second_id")
    private String secondId;

    @Column(name = "schedule_day_id")
    private String scheduleDayId;

    @ManyToOne()
    @JoinColumn(name = "schedule_day_id")
    @MapsId("scheduleDayId")
    private ScheduleDay scheduleDay;

    public Match(){}

    public Match(String id, String scheduleDayId, String firstId, String secondId) {
        super(id);
        this.scheduleDayId = scheduleDayId;
        this.firstId = firstId;
        this.secondId = secondId;
    }

    public boolean includeTeam(String id) {
        return this.firstId.equals(id) || this.secondId.equals(id);
    }

    @Override
    public Match deepClone() {
        return new Match(
                getId(),
                this.scheduleDayId,
                this.firstId,
                this.secondId
        );
    }


}
