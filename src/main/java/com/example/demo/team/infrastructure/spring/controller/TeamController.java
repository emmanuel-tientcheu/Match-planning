package com.example.demo.team.infrastructure.spring.controller;

import an.awesome.pipelinr.Pipeline;
import com.example.demo.player.application.useCase.CreatePlayerCommand;
import com.example.demo.player.application.useCase.GetPlayerByIdCommand;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.player.domain.viewmodel.PlayerViewModel;
import com.example.demo.player.infrastructure.spring.CreatePlayerDTO;
import com.example.demo.team.application.usecases.AddPlayerToTeamCommand;
import com.example.demo.team.application.usecases.CreateTeamCommand;
import com.example.demo.team.application.usecases.GetTeamByIdCommand;
import com.example.demo.team.application.usecases.RemovePlayerFromTeamCommand;
import com.example.demo.team.domaine.viewmodel.TeamViewModel;
import com.example.demo.team.infrastructure.spring.dto.AddPlayerToTeamDto;
import com.example.demo.team.infrastructure.spring.dto.CreateTeamDto;
import com.example.demo.team.infrastructure.spring.dto.RemovePlayerFromTeamDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@Transactional
public class TeamController {
    private final Pipeline pipeline;

    public TeamController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamViewModel> getPlayerById(
            @PathVariable("id") String id
    ) {
        var result = this.pipeline.send(new GetTeamByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDto dto) {
        var result = this.pipeline.send(new CreateTeamCommand(dto.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(
            @RequestBody AddPlayerToTeamDto dto
            ) {
        var result = this.pipeline.send(new AddPlayerToTeamCommand(dto.getTeamId(), dto.getPlayerId(), dto.getRole()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove-player-to-team")
    public ResponseEntity<Void> removePlayerFromTeam(
            @RequestBody RemovePlayerFromTeamDto dto
    ) {
        var result = this.pipeline.send(new RemovePlayerFromTeamCommand(dto.getTeamId(), dto.getPlayerId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
