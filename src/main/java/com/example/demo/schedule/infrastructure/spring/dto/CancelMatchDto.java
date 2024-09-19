package com.example.demo.schedule.infrastructure.spring.dto;

public class CancelMatchDto {

    private String matchId;

    public CancelMatchDto(){}

    public CancelMatchDto(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchId() {
        return matchId;
    }
}
