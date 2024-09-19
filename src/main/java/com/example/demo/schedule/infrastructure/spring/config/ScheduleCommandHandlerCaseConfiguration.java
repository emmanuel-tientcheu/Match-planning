package com.example.demo.schedule.infrastructure.spring.config;

import com.example.demo.schedule.application.ports.ScheduleDayRepository;
import com.example.demo.schedule.application.usecases.CancelMatchCommandHandler;
import com.example.demo.schedule.application.usecases.OrganizeMatchCommandHandler;
import com.example.demo.team.application.ports.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleCommandHandlerCaseConfiguration {

    @Bean
    public OrganizeMatchCommandHandler organizeMatchCommandHandler(
            TeamRepository teamRepository,
            ScheduleDayRepository scheduleDayRepository
    ) {
        return new OrganizeMatchCommandHandler(teamRepository, scheduleDayRepository);
    }

    @Bean
    public CancelMatchCommandHandler cancelMatchCommandHandler(ScheduleDayRepository scheduleDayRepository) {
        return new CancelMatchCommandHandler(scheduleDayRepository);
    }
}
