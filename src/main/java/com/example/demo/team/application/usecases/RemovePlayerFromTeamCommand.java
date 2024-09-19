package com.example.demo.team.application.usecases;

import an.awesome.pipelinr.Command;

public class RemovePlayerFromTeamCommand implements Command<Void> {
    private String teamId;
    private String playerId;

    public RemovePlayerFromTeamCommand(String teamId, String playerId) {
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
