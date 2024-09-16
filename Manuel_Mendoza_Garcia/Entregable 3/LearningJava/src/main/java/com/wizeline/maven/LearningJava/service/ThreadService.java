package com.wizeline.maven.LearningJava.service;

import com.wizeline.maven.LearningJava.LearningJavaApplication;
import com.wizeline.maven.LearningJava.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ThreadService {
    private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());

    @Autowired
    UserService userService;

    @Async
    public ResponseDTO createUserThread(String user, String password) throws InterruptedException  {
        ResponseDTO response = new ResponseDTO();
        LOGGER.info("Iniciando creación de usuario " + user + " en el hilo " + Thread.currentThread().getName());
        response = userService.createUser(user, password);
        Thread.sleep(500);
        LOGGER.info("Usuario creado exitosamente");
        return response;
    }
}
