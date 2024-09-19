package com.example.demo.team.infrastructure.spring.dto;

import com.example.demo.team.domaine.model.Role;

public class AddPlayerToTeamDto {
    private String playerId;
    private String teamId;
    private String role;

    public AddPlayerToTeamDto(String playerId, String teamId, String role) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.role = role;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public Role getRole() {
        return Role.fromString(this.role);
    }
}