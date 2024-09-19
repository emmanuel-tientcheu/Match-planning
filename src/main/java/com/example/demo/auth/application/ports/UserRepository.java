package com.example.demo.auth.application.ports;

import com.example.demo.auth.domain.User;
import com.example.demo.core.infrastructure.persistence.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
    boolean isEmailAdressAvailable(String email);
    Optional<User> findByEmail(String email);
}
