package com.example.demo.equipe.infrastructure.persistance.ram;

import com.example.demo.core.infrastructure.persistence.ram.InMemoryBaseEntity;
import com.example.demo.equipe.application.ports.EquipeRepository;
import com.example.demo.equipe.domaine.model.Equipe;

public class InMemoryEquipeRepository extends InMemoryBaseEntity<Equipe> implements EquipeRepository {
}
