package com.example.fishapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fishapi.entity.Fish;

import org.junit.jupiter.api.Test;
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
public class FishIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void Should_ListAllFishes_When_Get() throws Exception {
        List<Fish> fishes = List.of(
                new Fish(
                        1L,
                        "salmon",
                        LocalDate.of(2022,8,1),
                        13,
                        6.99
                ),
                new Fish(
                        2L,
                        "haddock",
                        LocalDate.of(2022,7,30),
                        36,
                        3.50
                ),
                new Fish(
                        3L,
                        "mackerel",
                        LocalDate.of(2022,8,8),
                        55,
                        3.6
                ),
                new Fish(
                        4L,
                        "cod",
                        LocalDate.of(2022,7,27),
                        24,
                        4.25
                ),
                new Fish(
                        5L,
                        "sea bass",
                        LocalDate.of(2022,8,4),
                        43,
                        3.85
                ));

        this.mvc.perform(
                        get("/get/all"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(this.mapper.writeValueAsString(fishes)));
    }

    @Test
    void Should_GetOneFish_When_GetWithId() throws Exception {
        this.mvc.perform(
                        get("/get/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(this.mapper.writeValueAsString(new Fish(1L, "salmon", LocalDate.parse("2022-08-01"), 13, 6.99))));
    }

    @Test
    void Should_CreateFish_When_Post() throws Exception {
        this.mvc.perform(
                        post("/post").content(this.mapper.writeValueAsString(new Fish("Tuna", LocalDate.of(2022, 6,30), 25, 11.05)))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().json(this.mapper.writeValueAsString(new Fish(6L, "Tuna", LocalDate.of(2022, 6,30), 25, 11.05))));
    }

    @Test
    void Should_ModifyFish_When_PatchWithAllParams() throws Exception {
        this.mvc.perform(
                        patch("/patch/1")
                                .param("name", "whitebait")
                                .param("dateCaught", "2021-01-01")
                                .param("quantity", "9999")
                                .param("price", "0.01"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(this.mapper.writeValueAsString(new Fish(1L, "whitebait", LocalDate.parse("2021-01-01"), 9999, 0.01))));
    }

    @Test
    void Should_ModifyFish_When_PatchWithOneParam() throws Exception {
        this.mvc.perform(
                        patch("/patch/1")
                                .param("dateCaught", "2021-01-01"))
                        .andExpect(status().isOk())
                        .andExpect(content().json(this.mapper.writeValueAsString(new Fish(1L, "salmon", LocalDate.parse("2021-01-01"), 13, 6.99))));
    }

    @Test
    void Should_BadRequest_When_PatchWithFakeId() throws Exception {
        this.mvc.perform(
                        patch("/patch/999")
                                .param("name", "tuna"))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void Should_BadRequest_When_PatchWithNoParams() throws Exception {
        this.mvc.perform(
                        patch("/patch/1"))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void Should_DeleteFish_When_DeleteWithId() throws Exception {
        this.mvc.perform(
                        delete("/delete/1"))
                        .andExpect(status().isOk());
    }

    @Test
    void Should_InternalServerError_When_DeleteWithFakeId() throws Exception {
        this.mvc.perform(
                        delete("/delete/777"))
                        .andExpect(status().isInternalServerError());
    }
}
