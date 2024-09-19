package com.example.demo.auth.infrastructure.persistence.jpa;

import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.domain.User;
import com.example.demo.core.infrastructure.persistence.sql.SqlBaseRepository;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SQLUserRepository extends SqlBaseRepository<User> implements UserRepository {
    private final SQLUserAccessor userAccessor;

    public SQLUserRepository(EntityManager entityManager, SQLUserAccessor userAccessor) {
        super(entityManager);
        this.userAccessor = userAccessor;
    }
    @Override
    public boolean isEmailAdressAvailable(String email) {
        return !userAccessor.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userAccessor.findByEmail(email));
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
