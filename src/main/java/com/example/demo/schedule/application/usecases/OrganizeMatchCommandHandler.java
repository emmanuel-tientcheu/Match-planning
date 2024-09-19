package com.example.demo.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.core.domain.exception.NotFoundException;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.domaine.model.ScheduleDay;
import com.example.demo.team.application.ports.TeamRepository;

import java.time.LocalDate;
import java.util.UUID;

public class OrganizeMatchCommandHandler implements Command.Handler<OrganizeMatchCommand, IdResponse> {

    private final TeamRepository teamRepository;
    private final ScheduleDayRepository scheduleDayRepository;

    public OrganizeMatchCommandHandler(TeamRepository teamRepository, ScheduleDayRepository scheduleDayRepository) {
        this.teamRepository = teamRepository;
        this.scheduleDayRepository = scheduleDayRepository;
    }

    @Override
    public IdResponse handle(OrganizeMatchCommand command) {
        var t1 = teamRepository.findById(command.getFirstTeam()).orElseThrow(
                () -> new NotFoundException("THIS TEAM NOT EXSIST", command.getFirstTeam())
        );

        var t2 = teamRepository.findById(command.getSecondTeam()).orElseThrow(
                () -> new NotFoundException("THIS TEAM NOT EXSIST", command.getSecondTeam())
        );

        var scheduleDay = scheduleDayRepository.findByDate(command.getDate()).orElse(
                 new ScheduleDay(
                        UUID.randomUUID().toString(),
                        command.getDate()
                )
        );
        var organizeMatch = scheduleDay.organize(t1, t2, command.getMoment());
        scheduleDayRepository.save(scheduleDay);

        return new IdResponse(organizeMatch.getId());
    }
}
