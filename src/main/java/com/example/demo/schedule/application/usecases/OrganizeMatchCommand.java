package com.example.demo.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.schedule.domaine.model.Moment;

import java.time.LocalDate;

public class OrganizeMatchCommand implements Command<IdResponse> {
    private LocalDate date;
    private Moment moment;
    private String firstTeam;
    private String secondTeam;

    public OrganizeMatchCommand(LocalDate date, Moment moment, String firstTeam, String secondTeam) {
        this.date = date;
        this.moment = moment;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public LocalDate getDate() {
        return date;
    }

    public Moment getMoment() {
        return moment;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }
}
