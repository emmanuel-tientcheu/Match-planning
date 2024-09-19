package com.example.demo.player.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import com.example.demo.player.application.useCase.CreatePlayerCommand;
import com.example.demo.player.application.useCase.DeletePlayerCommand;
import com.example.demo.player.application.useCase.GetPlayerByIdCommand;
import com.example.demo.player.application.useCase.RenamePlayerCommand;
import com.example.demo.player.domain.viewmodel.IdResponse;
import com.example.demo.player.domain.viewmodel.PlayerViewModel;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@Transactional
public class PlayerController {

   // private final UserRepository playerRepository; version 1
   // private final CreatePlayerUseCase createPlayerUseCase version 2;
    private final Pipeline pipeline;

   // public PlayerController(CreatePlayerUseCase createPlayerUseCase) { this.createPlayerUseCase = createPlayerUseCase; } version 2
    public PlayerController(Pipeline pipeline) { this.pipeline = pipeline; }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerViewModel> getPlayerById(
            @PathVariable("id") String id
    ) {
        var result = this.pipeline.send(new GetPlayerByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDTO dto) {
       // var result = this.createPlayerUseCase.execute(dto.getName());
       // var result = this.createPlayerUseCase.handle(new CreatePlayerCommand(dto.getName()));
        var result = this.pipeline.send(new CreatePlayerCommand(dto.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> changePlayerName(
       @RequestBody RenamePlayerDTO dto,
       @PathVariable("id") String id
    ) {
        this.pipeline.send(new RenamePlayerCommand(id, dto.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable("id") String id
    ) {
        this.pipeline.send(new DeletePlayerCommand(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
