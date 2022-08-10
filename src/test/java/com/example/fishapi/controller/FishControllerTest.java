package com.example.fishapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fishapi.entity.Fish;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:fish-schema.sql",
                "classpath:fish-data.sql"}, executionPhase  = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class FishControllerTest {

    private String path = "/api/v1/fish";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testPostFish() throws Exception {
        this.mvc.perform(
                        post(path + "/post").content(this.mapper.writeValueAsString(new Fish("Tuna", LocalDate.of(2022, 6,30), 25, 11.05)))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().json(this.mapper.writeValueAsString(new Fish(6L, "Tuna", LocalDate.of(2022, 6,30), 25, 11.05))));
    }

    @Test
    void testGetFish() throws Exception {
        List<Fish> fishes = List.of(
                new Fish(
                        "Salmon",
                        LocalDate.of(2022,8,1),
                        13,
                        6.99
                ),
                new Fish(
                        "Haddock",
                        LocalDate.of(2022,7,30),
                        36,
                        3.50
                ),
                new Fish(
                        "Mackerel",
                        LocalDate.of(2022,8,8),
                        55,
                        3.6
                ),
                new Fish(
                        "Cod",
                        LocalDate.of(2022,7,27),
                        24,
                        4.25
                ),
                        new Fish(
                        "Sea bass",
                        LocalDate.of(2022,8,4),
                        43,
                        3.85
                ));

        this.mvc.perform(
                        get("/get/All"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(this.mapper.writeValueAsString(fishes)));
    }
}
