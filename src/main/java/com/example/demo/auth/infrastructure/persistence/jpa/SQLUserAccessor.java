package com.example.demo.auth.infrastructure.persistence.jpa;

import com.example.demo.auth.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SQLUserAccessor extends CrudRepository<User, String>  {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
