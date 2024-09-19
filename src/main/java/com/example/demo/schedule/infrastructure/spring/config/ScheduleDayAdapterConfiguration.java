package com.example.demo.schedule.infrastructure.spring.config;

import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.infrastructure.persistence.jpa.SQLScheduleDayRepository;
import com.example.demo.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleDayAdapterConfiguration {
    @Bean
    public ScheduleDayRepository scheduleDayRepository(EntityManager entityManager) {
        return new SQLScheduleDayRepository(entityManager);
    }
}
