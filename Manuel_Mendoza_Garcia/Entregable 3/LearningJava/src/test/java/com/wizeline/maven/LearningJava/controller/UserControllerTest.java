package com.wizeline.maven.LearningJava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.maven.LearningJava.model.BankAccountDTO;
import com.wizeline.maven.LearningJava.model.Post;
import com.wizeline.maven.LearningJava.model.ResponseDTO;
import org.apache.juli.logging.Log;
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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class UserControllerTest {

    private static final Logger LOGGER = Logger.getLogger(UserControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createUserAccount() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(post("/api/createUser")
                .content("{\"user\": \"user11@wizeline.com\", \"password\": \"Pass11$\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        ResponseDTO responseDTO = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), ResponseDTO.class);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertEquals("success",responseDTO.getStatus());
    }

    @Test
    void createUsersAccount() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(post("/api/createUsers")
                .content("[{\"user\": \"user12@wizeline.com\", \"password\": \"Pass12$\"},{\"user\": \"user13@wizeline.com\", \"password\": \"Pass13$\"},{\"user\": \"user14@wizeline.com\", \"password\": \"Pass14$\"}]")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        ResponseDTO[] responsesArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), ResponseDTO[].class);
        List<ResponseDTO> responsesResultado = Arrays.asList(responsesArreglo);

        Assertions.assertTrue(responsesResultado.stream()
                .map(ResponseDTO::getStatus)
                .collect(Collectors.toList())
                .containsAll(List.of("success"))
        );
    }

    @Test
    void createDefaultUsers() throws  Exception{
        MvcResult resultadoPeticion = mockMvc.perform(post("/api/createDefaultUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        ResponseDTO[] responsesArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), ResponseDTO[].class);
        List<ResponseDTO> responsesResultado = Arrays.asList(responsesArreglo);

        Assertions.assertTrue(responsesResultado.stream()
                .map(ResponseDTO::getCode)
                .collect(Collectors.toList())
                .containsAll(List.of("OK000"))
        );
    }

    @Test
    void getUsers() throws Exception{
        for(int i = 0; i < 5; i++) {
            MvcResult resultadoPeticion =
                    mockMvc.perform(get("/api/users"))
                            .andExpect(status().isOk())
                            .andReturn();
        }

        MvcResult resultadoPeticion =
                mockMvc.perform(get("/api/users"))
                        .andExpect(status().isTooManyRequests())
                        .andReturn();
        LOGGER.info(String.valueOf(resultadoPeticion.getResponse().getStatus()));

        Assertions.assertEquals(429,resultadoPeticion.getResponse().getStatus());
    }

    @Test
    void loginUser() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(get("/api/login")
                .param("user","user11@wizeline.com")
                .param("password", "Pass11"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        ResponseDTO responseDTO = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), ResponseDTO.class);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertEquals("fail",responseDTO.getStatus());

    }

}