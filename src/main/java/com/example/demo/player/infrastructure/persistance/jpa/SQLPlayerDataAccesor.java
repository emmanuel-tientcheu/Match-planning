package com.example.demo.player.infrastructure.persistance.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SQLPlayerDataAccesor extends JpaRepository<SQLPlayer, String> {
}
