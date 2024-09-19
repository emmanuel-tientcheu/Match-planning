package com.example.demo.core.infrastructure.persistence.ram;

import com.example.demo.core.domain.model.BaseEntity;
import com.example.demo.core.infrastructure.persistence.BaseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryBaseEntity<T extends BaseEntity<T>> implements BaseRepository<T> {
    protected Map<String, T> entities = new HashMap<>();

    @Override
    public Optional<T> findById(String id) {
        return this.entities.get(id) == null ?  Optional.empty() : Optional.of(this.entities.get(id).deepClone());
      //  return Optional.ofNullable(this.entities.get(id));
    }

    @Override
    public void save(T entitie) {
        this.entities.put(entitie.getId(), entitie.deepClone());
    }

    @Override
    public void delete(T entitie) {
        this.entities.remove(entitie.getId());
    }

    @Override
    public void clear() { this.entities.clear();}
}
