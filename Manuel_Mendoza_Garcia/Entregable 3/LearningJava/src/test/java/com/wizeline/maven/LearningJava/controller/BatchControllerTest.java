package com.wizeline.maven.LearningJava.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class BatchControllerTest {

    private static final Logger LOGGER = Logger.getLogger(BatchControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Test
    void startBatch() throws Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/batch/start"))
                        .andExpect(status().isOk())
                        .andReturn();
        LOGGER.info(String.valueOf(resultadoPeticion.getResponse().getStatus()));

        Assertions.assertEquals(200,resultadoPeticion.getResponse().getStatus());
    }
}