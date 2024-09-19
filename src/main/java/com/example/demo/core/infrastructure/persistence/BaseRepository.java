package com.example.demo.core.infrastructure.persistence;

import com.example.demo.core.domain.model.BaseEntity;

import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {
    public Optional<T> findById(String id);
    public void save(T entity);
    public void delete(T entity);
    public void clear();
}
