package com.example.demo.schedule.infrastructure.spring.dto;

import com.example.demo.schedule.domaine.model.Moment;

import java.time.LocalDate;

public class OrganizeMatchDto {
    private String date;
    private String moment;
    private String firstTeamId;
    private String secondTeamId;

    public OrganizeMatchDto(){}

    public OrganizeMatchDto(String date, String moment, String firstTeamId, String secondTeamId) {
        this.date = date;
        this.moment = moment;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
    }

    public String getFirstTeamId() {
        return firstTeamId;
    }

    public String getSecondTeamId() {
        return secondTeamId;
    }

    public String getDate() {
       return this.date;
    }

    public LocalDate toLocalDate() {
        return LocalDate.parse(this.date);
    }


    public Moment getMoment() {
        return Moment.fromString(this.moment);
    }
}
