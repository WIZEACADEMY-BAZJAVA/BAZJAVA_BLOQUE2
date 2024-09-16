package com.superapp.springboot.learningjava.service;

import com.superapp.springboot.learningjava.dto.ResponseDTO;

public interface UserService {

	ResponseDTO createUser(String user, String password);
    ResponseDTO login(String user, String password);
    
}
