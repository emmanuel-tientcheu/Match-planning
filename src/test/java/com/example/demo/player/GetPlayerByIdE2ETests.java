package com.example.demo.player;

import com.example.demo.IntegrationTests;
import com.example.demo.PostgreSQLTestConfiguration;
import com.example.demo.auth.application.ports.UserRepository;
import com.example.demo.auth.application.services.jwtservices.JwtService;
import com.example.demo.auth.domain.User;
import com.example.demo.player.application.ports.PlayerRepository;
import com.example.demo.player.domain.model.Player;
import com.example.demo.player.domain.viewmodel.PlayerViewModel;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
public class GetPlayerByIdE2ETests extends IntegrationTests {

    @Test
    public void ShouldGetPlayerById() throws Exception {


        var player = new Player("123", "player");
        playerRepository.save(player);
        var result =  mockMvc
                .perform(MockMvcRequestBuilders.
                        get("/players/" + player.getId())
                        .header("Authorization", createJwt())
                )

                .andReturn();

        var viewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class
        );

        Assert.assertEquals(viewModel.getName(), player.getName());
    }
}
