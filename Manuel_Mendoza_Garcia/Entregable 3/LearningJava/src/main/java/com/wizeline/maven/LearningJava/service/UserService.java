package com.wizeline.maven.LearningJava.service;

import com.wizeline.maven.LearningJava.model.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

	ResponseDTO createUser(String user, String password);
	
	ResponseDTO login(String user, String password);

}
