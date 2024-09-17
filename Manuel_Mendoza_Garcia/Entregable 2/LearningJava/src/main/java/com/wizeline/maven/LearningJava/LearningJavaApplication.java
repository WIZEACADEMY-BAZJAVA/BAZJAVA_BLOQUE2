package com.wizeline.maven.LearningJava;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import com.wizeline.maven.LearningJava.model.ResponseDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.wizeline.maven.LearningJava.service.BankAccountService;
import com.wizeline.maven.LearningJava.service.BankAccountServiceImpl;
import com.wizeline.maven.LearningJava.service.UserService;
import com.wizeline.maven.LearningJava.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LearningJavaApplication extends Thread {

	private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());
	public static final String SUCCESS_CODE = "OK000";


	@Bean
	public static UserService userService() {
		return new UserServiceImpl();
	}
	@Bean
	public static BankAccountService bankAccountService(){
		return new BankAccountServiceImpl();
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


	public static void main(String[] args) throws IOException {
		SpringApplication.run(LearningJavaApplication.class, args);
	}
}