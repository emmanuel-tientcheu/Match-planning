package com.example.demo.core.infrastructure.persistence.sql;

import com.example.demo.core.domain.model.BaseEntity;
import com.example.demo.core.infrastructure.persistence.BaseRepository;

import jakarta.persistence.EntityManager;

import java.util.Optional;

public abstract class SqlBaseRepository<T extends BaseEntity<T>> implements BaseRepository<T> {
    protected final EntityManager entityManager;

    protected SqlBaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(T player) {
       entityManager.remove(player);
    }

    public abstract  Class<T> getEntityClass();

    @Override
    public void clear() {
        entityManager.createQuery("DELETE FROM " + getEntityClass().getSimpleName()).executeUpdate();
    }

 }
