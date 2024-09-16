package com.wizeline.maven.LearningJava;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.wizeline.maven.LearningJava.service.BankAccountService;
import com.wizeline.maven.LearningJava.service.BankAccountServiceImpl;
import com.wizeline.maven.LearningJava.service.UserService;
import com.wizeline.maven.LearningJava.service.UserServiceImpl;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class LearningJavaApplication extends Thread {

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