package com.example.demo.auth.infrastructure.spring;

import com.example.demo.auth.application.services.jwtservices.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private final JwtService jwtservice;

    public JwtAuthentificationFilter(JwtService jwtservice) {
        this.jwtservice = jwtservice;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
            var token = request.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                var jwt = token.substring(7);
                var user = this.jwtservice.parse(jwt);
                if(user !=null) {
                    var authorities = new ArrayList<GrantedAuthority>();
                    authorities.add(new SimpleGrantedAuthority("User"));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
    }
}
