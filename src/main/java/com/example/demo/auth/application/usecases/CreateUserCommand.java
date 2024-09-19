package com.example.demo.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.example.demo.player.domain.viewmodel.IdResponse;

public class CreateUserCommand implements Command<IdResponse> {
    private String email;
    private String password;

    public CreateUserCommand() {}

    public CreateUserCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
