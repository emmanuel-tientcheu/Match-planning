package com.example.demo.schedule.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.example.demo.player.application.useCase.CreatePlayerCommand;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.player.infrastructure.spring.CreatePlayerDTO;
import com.example.demo.schedule.application.usecases.CancelMatchCommand;
import com.example.demo.schedule.application.usecases.OrganizeMatchCommand;
import com.example.demo.schedule.infrastructure.spring.dto.CancelMatchDto;
import com.example.demo.schedule.infrastructure.spring.dto.OrganizeMatchDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleDayController {
    private final Pipeline pipeline;

    public ScheduleDayController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("/organize-match")
    public ResponseEntity<IdResponse> organizeMatch(@RequestBody OrganizeMatchDto dto) {
        var result = this.pipeline.send(new OrganizeMatchCommand(dto.toLocalDate(), dto.getMoment(), dto.getFirstTeamId(), dto.getSecondTeamId()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("cancel-match")
    public ResponseEntity<Void> cancelMatch(@RequestBody CancelMatchDto dto) {
        var result = this.pipeline.send(new CancelMatchCommand(dto.getMatchId()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
