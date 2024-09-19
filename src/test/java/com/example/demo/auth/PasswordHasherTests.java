package com.example.demo.auth;

import com.example.demo.auth.application.services.passwordHasher.BcryptPasswordHasher;
import com.example.demo.auth.application.services.passwordHasher.PasswordHasher;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PasswordHasherTests {

    public PasswordHasher createHasher() {
        return new BcryptPasswordHasher();
    }

    @Test
    public void shouldPasswordHasher() {
        var hasher = createHasher();

        var clear = "qwertz";
        var hashedPassword = hasher.hash(clear);

        Assert.assertTrue(
            hasher.match(clear, hashedPassword)
        );
    }
}
