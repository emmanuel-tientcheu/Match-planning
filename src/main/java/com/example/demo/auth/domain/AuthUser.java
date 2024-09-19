package com.example.demo.auth.domain;

public class AuthUser {
    private String id;
    private String email;

    public AuthUser(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
