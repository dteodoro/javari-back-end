package com.dteodoro.javari.game.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dteodoro.javari.game.service.BetService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BetControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    BetService betService;

    @Test
    @DisplayName("should return 200 if bettorId is valid")
    void testFindLastBets_bettorValid() throws Exception {
        var response = mvc.perform(
                get("/api/v1/game/bets/{bettorId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("should return 404 if bettorId is blank")
    void testFindLastBets_bettorBlank() throws Exception {
        var response = mvc.perform(
                get("/api/v1/game/bets/{bettorId}", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("should return 404 if bettorId is invalid")
    void testFindLastBets_bettorInvalid() throws Exception {
        var response = mvc.perform(
                get("/api/v1/game/bets/{bettorId}", "INVALID_UUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("sould return code 200 if betDTO is valid")
    void testMakeBet_betValid() throws Exception {
        String bet = """
                    {
                        "id":"88eab3ae-a7d7-44a3-bb3e-8e7f6cebadaf",
                        "bettorId":"16e80035-cb57-40d0-a48b-fea0587c4ec3",
                        "scheduleId":"158ec88e-138c-4b62-bdc9-3397694a60b7",
                        "bet":"HOME"
                    }
                """;

        var response = mvc.perform(
                post("/api/v1/game/bets")
                        .contentType(MediaType.APPLICATION_JSON).content(bet))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("sould return code 200 if betDTO is invalid")
    void testMakeBet_batInvalid() throws Exception {
        String bet = """
                    {
                        "id":"1213213",
                        "bettorId":"16e80035-cb57-40d0-a48b-fea0587c4ec3",
                        "scheduleId":"158ec88e-138c-4b62-bdc9-3397694a60b7",
                        "bet":"HOME"
                    }
                """;

        var response = mvc.perform(
                post("/api/v1/game/bets")
                        .contentType(MediaType.APPLICATION_JSON).content(bet))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    @DisplayName("sould return code 200 if betDTO is blank")
    void testMakeBet_batNull() throws Exception {
        String bet = "{}";

        var response = mvc.perform(
                post("/api/v1/game/bets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}
