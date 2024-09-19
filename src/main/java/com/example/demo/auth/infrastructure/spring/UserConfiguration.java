package com.example.demo.auth.infrastructure.spring;

import com.example.demo.auth.application.ports.AuthContext;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.infrastructure.auth.spring.SpringAuthContext;
import com.example.demo.auth.infrastructure.persistence.jpa.SQLUserAccessor;
import com.example.demo.auth.infrastructure.persistence.jpa.SQLUserRepository;
import com.example.demo.auth.infrastructure.persistence.ram.InMemoryUserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    /*@Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }*/
    @Bean
    public UserRepository userRepository(
            EntityManager entityManager,
            SQLUserAccessor userAccessor
    ) {
        return new SQLUserRepository(entityManager, userAccessor);
    }
    @Bean
    public AuthContext authContext() {
        return new SpringAuthContext();
    }
}
