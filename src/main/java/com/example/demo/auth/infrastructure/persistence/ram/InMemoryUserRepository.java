package com.example.demo.auth.infrastructure.persistence.ram;

import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.domain.User;
import com.example.demo.core.infrastructure.persistence.ram.InMemoryBaseEntity;

import java.util.Optional;

public class InMemoryUserRepository extends InMemoryBaseEntity<User> implements UserRepository {
    @Override
    public boolean isEmailAdressAvailable(String email) {
        return entities
                .values()
                .stream()
                .noneMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return entities
                .values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }


}
