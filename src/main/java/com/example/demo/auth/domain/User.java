package com.example.demo.auth.domain;

import com.example.demo.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity<User> {
    @Column
    private String email;
    @Column

    private String password;

    public User(String id, String email, String password) {
        super(id);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public User deepClone() {
        return new User(this.id, this.email, this.password);
    }
}
