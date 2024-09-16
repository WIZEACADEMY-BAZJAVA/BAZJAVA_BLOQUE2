package com.wizeline.maven.LearningJava.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.wizeline.maven.LearningJava.LearningJavaApplication;
import com.wizeline.maven.LearningJava.config.EndpointBean;
import com.wizeline.maven.LearningJava.config.KafkaConfiguration;
import com.wizeline.maven.LearningJava.config.SecurityConfig;
import com.wizeline.maven.LearningJava.model.BankAccountDTO;
import com.wizeline.maven.LearningJava.model.Post;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class BankingAccountControllerTest {

    private static final Logger LOGGER = Logger.getLogger(BankingAccountControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void sayHelloEndpointTest() throws Exception {
        String resultadoPeticion = mockMvc.perform(get("/api/sayHello"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LOGGER.info(resultadoPeticion);
        Assertions.assertEquals("Hola invitado!!", resultadoPeticion);
    }

    @Test
    void getAccountsTest() throws Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/api/getAccounts"))
                        .andExpect(status().isOk())
                        .andReturn();
        BankAccountDTO[] accountsArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), BankAccountDTO[].class);
        List<BankAccountDTO> accountsResultado = Arrays.asList(accountsArreglo);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertFalse(accountsResultado.stream()
                .map(BankAccountDTO::getUserName)
                .collect(Collectors.toList())
                .containsAll(List.of("user1@wizeline.com","user2@wizeline.com","user4@wizeline.com"))
        );
    }

    @Test
    void getExternalUserTest() throws  Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/api/getExternalUser/{userId}",7))
                        .andExpect(status().isOk())
                        .andReturn();

        Post postResultado = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), Post.class);
        LOGGER.info(postResultado.getBody());

        Assertions.assertEquals(7,postResultado.getId());

    }

    @Test
    void sendUserAccountTest() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(post("/api/send/{userId}",2)
                .content(mapper.writeValueAsString(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.toString());
    }

    @Test
    void updateUserAccountsTest() throws Exception {
        MvcResult resultadoPeticion =
                mockMvc.perform(put("/api/updateUserAccounts")
                .param("oldUser","user3@wizeline.com")
                        .param("newUser", "user1@wizeline.com")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        BankAccountDTO[] accountsArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), BankAccountDTO[].class);
        List<BankAccountDTO> accountsResultado = Arrays.asList(accountsArreglo);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertFalse(accountsResultado.stream()
                .map(BankAccountDTO::getUserName)
                .collect(Collectors.toList())
                .containsAll(List.of("user3@wizeline.com"))
        );

    }

    @Test
    void deleteAccountsTest() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(delete("/api/deleteAccounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertEquals("All accounts deleted",resultadoPeticion.getResponse().getContentAsString());

    }

    @Test
    @WithMockUser("USER")
    void getUserAccountTest() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(get("/api/getUserAccount")
                .param("user", "user10@wizeline.com")
                .param("password", "Pass10$")
                .param("date", "12-08-1991")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        BankAccountDTO bankAccountResultado = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), BankAccountDTO.class);
        Assertions.assertEquals("user10@wizeline.com",bankAccountResultado.getUserName());
    }

    @Test
    @WithMockUser("USER")
    void getAccountByUser() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(get("/api/getAccountByUser")
                .param("user","user1@wizeline.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BankAccountDTO[] accountsArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), BankAccountDTO[].class);
        List<BankAccountDTO> accountsResultado = Arrays.asList(accountsArreglo);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertTrue(accountsResultado.stream()
                .map(BankAccountDTO::getUserName)
                .collect(Collectors.toList())
                .containsAll(List.of("user1@wizeline.com"))
        );

    }

    @Test
    @WithMockUser("USER")
    void getAccountsGroupByType() throws Exception{
        String resultadoPeticion = mockMvc.perform(get("/api/getAccountsGroupByType")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LOGGER.info(resultadoPeticion);
        Assertions.assertNotNull(resultadoPeticion);
    }
}