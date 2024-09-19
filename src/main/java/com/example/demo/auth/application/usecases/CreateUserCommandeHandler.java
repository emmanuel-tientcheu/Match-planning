package com.example.demo.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import com.example.demo.auth.domain.User;
import com.example.demo.core.domain.exception.BadRequestException;
import com.example.demo.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class CreateUserCommandeHandler implements Command.Handler<CreateUserCommand, IdResponse> {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public CreateUserCommandeHandler(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public IdResponse handle(CreateUserCommand createUserCommand) {
        var isEmailAvailable = userRepository.isEmailAdressAvailable(createUserCommand.getEmail());

        if(!isEmailAvailable) {
           // throw new IllegalArgumentException("Email Address is already in use");
            throw new BadRequestException("Email Adress is already in use");
        }
        User user = new User(
                UUID.randomUUID().toString(),
                createUserCommand.getEmail(),
                passwordHasher.hash(createUserCommand.getPassword())
        );

        this.userRepository.save(user);
        return  new IdResponse(user.getId());
    }
}
