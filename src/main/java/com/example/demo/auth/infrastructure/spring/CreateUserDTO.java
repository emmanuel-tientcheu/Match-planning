package com.example.demo.auth.infrastructure.spring;

public class CreateUserDTO {
    private String email;
    private String password;

    public CreateUserDTO(String email, String password) {
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
