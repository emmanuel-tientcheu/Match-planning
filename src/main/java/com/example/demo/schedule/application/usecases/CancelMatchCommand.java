package com.example.demo.schedule.application.usecases;

import an.awesome.pipelinr.Command;

public class CancelMatchCommand implements Command<Void> {
    private String matchId;

    public CancelMatchCommand(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchId() {
        return matchId;
    }
}
