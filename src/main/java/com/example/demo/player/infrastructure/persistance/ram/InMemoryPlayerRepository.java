package com.example.demo.player.infrastructure.persistance.ram;

import com.example.demo.core.infrastructure.persistence.ram.InMemoryBaseEntity;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPlayerRepository extends InMemoryBaseEntity<Player> implements PlayerRepository { }
