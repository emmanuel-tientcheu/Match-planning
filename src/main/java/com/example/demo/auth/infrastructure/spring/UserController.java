package com.example.demo.auth.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import com.example.demo.auth.application.usecases.CreateUserCommand;
import com.example.demo.auth.application.usecases.LoginCommand;
import com.example.demo.auth.domain.viewModel.LoggedUserViewModel;
import com.example.demo.player.application.useCase.CreatePlayerCommand;
import com.example.demo.player.domain.viewmodel.IdResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Transactional
public class UserController {
    private Pipeline pipeline;

    public UserController(Pipeline pipeline) { this.pipeline = pipeline; }

    @PostMapping("/register")
    public ResponseEntity<IdResponse> registerUser(
            @RequestBody CreateUserDTO dto
    ) {
        var result = this.pipeline.send(new CreateUserCommand(dto.getEmail(), dto.getPassword()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedUserViewModel> registerUser(
            @RequestBody LoginDTO dto
    ) {
        var result = this.pipeline.send(new LoginCommand(dto.getEmail(), dto.getPassword()));
        return ResponseEntity.ok(
                this.pipeline.send(
                        new LoginCommand(
                                dto.getEmail(),
                                dto.getPassword()
                        )
                )
        );
    }
}
