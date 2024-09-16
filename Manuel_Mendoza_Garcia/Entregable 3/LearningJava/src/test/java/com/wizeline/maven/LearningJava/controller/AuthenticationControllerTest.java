package com.wizeline.maven.LearningJava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class AuthenticationControllerTest {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAuthenticationToken() throws Exception {
        MvcResult resultadoPeticion = mockMvc.perform(post("/authenticate")
                .content("{\"user\": \"USER\", \"password\": \"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());
        Assertions.assertNotNull(resultadoPeticion.getResponse().getContentAsString());

    }
}