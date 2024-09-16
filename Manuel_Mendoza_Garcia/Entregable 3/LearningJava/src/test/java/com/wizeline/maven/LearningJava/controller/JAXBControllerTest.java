package com.wizeline.maven.LearningJava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.maven.LearningJava.model.ResponseDTO;
import com.wizeline.maven.LearningJava.model.XmlBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class JAXBControllerTest {

    private static final Logger LOGGER = Logger.getLogger(JAXBControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void loginUser() throws Exception {
        MvcResult resultadoPeticion = mockMvc.perform(get("/jaxb/getXML"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        XmlBean response = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), XmlBean.class);
        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertNotNull(response.getData());

    }

    @Test
    void listUsers() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(get("/jaxb/getXMLAPIPublica")
                .param("id","7"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        XmlBean response = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), XmlBean.class);
        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertNotNull(response.getData());

    }
}