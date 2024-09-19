package com.example.demo.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.schedule.application.ports.ScheduleDayRepository;

public class CancelMatchCommandHandler implements Command.Handler<CancelMatchCommand, Void> {
    private final ScheduleDayRepository scheduleDayRepository;

    public CancelMatchCommandHandler(ScheduleDayRepository scheduleDayRepository) {
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    public Void handle(CancelMatchCommand command) {
        var scheduleDay = scheduleDayRepository.findByMatchId(command.getMatchId())
                .orElseThrow(
                        () -> new NotFoundException("Match ", command.getMatchId())
                );
        scheduleDay.cancel(command.getMatchId());
        return null;
    }
}
