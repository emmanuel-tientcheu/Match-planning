package com.example.demo.team.infrastructure.spring.dto;

public class RemovePlayerFromTeamDto {
    private String teamId;
    private String playerId;

    public RemovePlayerFromTeamDto(String teamId, String playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }

}
