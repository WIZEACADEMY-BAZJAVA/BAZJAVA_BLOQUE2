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
class PatternDesignControllerTest {

    private static final Logger LOGGER = Logger.getLogger(PatternDesignControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getDatabaseConnection() throws Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/patterns/creation"))
                        .andExpect(status().isOk())
                        .andReturn();
        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertEquals("Connecting to MongoDB database ...",resultadoPeticion.getResponse().getContentAsString());
    }


    @Test
    void copyCommand() throws Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/patterns/behavioral"))
                        .andExpect(status().isOk())
                        .andReturn();
        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertEquals("BehavioralPattern Sucessfull",resultadoPeticion.getResponse().getContentAsString());
    }
}