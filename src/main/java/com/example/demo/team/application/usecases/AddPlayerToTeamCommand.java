package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.team.domaine.model.Role;

public class AddPlayerToTeamCommand implements Command<Void> {
    private String teamId;
    private String playerId;
    private Role role;

    public AddPlayerToTeamCommand(String teamId, String playerId, Role role) {
        this.teamId = teamId;
        this.playerId = playerId;
        this.role = role;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Role getRole() {
        return role;
    }
}
