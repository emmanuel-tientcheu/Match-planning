package com.example.demo.auth.infrastructure.spring;

import com.example.demo.auth.application.services.jwtservices.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
                .addFilterBefore(
                        new JwtAuthentificationFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(it ->
                        it
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(it -> it.disable())
                .httpBasic(it -> it.disable())
                .csrf(it -> it.disable())
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
