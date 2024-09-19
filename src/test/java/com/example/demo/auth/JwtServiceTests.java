package com.example.demo.auth;

import com.example.demo.auth.application.services.jwtservices.ConcreteJwtService;
import com.example.demo.auth.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class JwtServiceTests {

    @Test
    void shouldTokenizeUser() {
        var jwtService = new ConcreteJwtService(
                "super_secret_key_please_do_not_share",
                60
        );
        var user = new User("123", "emmanuel@gmail.com", "qwertz");

        var token = jwtService.tokenize(user);
        var authUser = jwtService.parse(token);

        Assert.assertNotNull(token);
        Assert.assertEquals(user.getId(), authUser.getId());
        Assert.assertEquals(user.getEmail(), authUser.getEmail());
    }
}
