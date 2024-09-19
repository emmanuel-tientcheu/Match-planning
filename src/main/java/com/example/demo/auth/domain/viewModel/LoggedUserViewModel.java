package com.example.demo.auth.domain.viewModel;

public class LoggedUserViewModel {
    private String id;
    private String email;
    private String token;

    public LoggedUserViewModel() {}

    public LoggedUserViewModel(String id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
