package com.example.demo.auth.application.services.jwtservices;


import com.example.demo.auth.domain.AuthUser;
import com.example.demo.auth.domain.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConcreteJwtService implements JwtService {
    private final SecretKey secretKey ;
    private final JwtParser jwtParser;
    private long expiration;

    public ConcreteJwtService(String secret,  long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtParser =  Jwts.parser().verifyWith(secretKey).build();
        this.expiration = expiration;
    }

    @Override
    public String tokenize(User user) {

        var claims = Jwts.claims()
                .subject(user.getId())
                .add("email", user.getEmail())
                .build();
        var createAt = LocalDateTime.now();
        var expiresAt = createAt.plusSeconds(this.expiration);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(
                        Date.from(createAt.atZone(ZoneId.systemDefault()).toInstant())
                )
                .expiration(
                        Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant())
                )
                .signWith(this.secretKey)
                .compact();
    }

    @Override
    public AuthUser parse(String token) {
        var claims = this.jwtParser.parseSignedClaims(token).getPayload();

        var id = claims.getSubject();
        var email = claims.get("email", String.class);

        return new AuthUser(id, email);
    }
}
