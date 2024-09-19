package com.example.demo.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.jwtservices.JwtService;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import com.example.demo.auth.domain.viewModel.LoggedUserViewModel;
import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.core.domain.exception.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedUserViewModel> {
    private UserRepository repository;
    private JwtService jwtService;
    private PasswordHasher passwordHasher;

    public LoginCommandHandler(UserRepository repository, JwtService jwtService, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoggedUserViewModel handle(LoginCommand loginCommand) {
        var user = repository.findByEmail(loginCommand.getEmail())
                .orElseThrow(
                        () ->  new NotFoundException("User", loginCommand.getEmail())
                );

        var match = this.passwordHasher.match(
                loginCommand.getPassword(),
                user.getPassword()
        );
        if(match == false) {
            throw new BadRequestException("Invalid password");
        }
        return new LoggedUserViewModel(
                user.getId(),
                user.getEmail(),
                jwtService.tokenize(user)
        );
    }
}
