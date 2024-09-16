package com.superapp.springboot.learningjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class LearningjavaApplication {

	private static final Logger LOGGER = Logger.getLogger(LearningjavaApplication.class.getName());

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningjavaApplication.class, args);
		LOGGER.info("LearningJava - Iniciado servicio REST ...");
	}

}
