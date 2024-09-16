package com.superapp.springboot.learningjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.superapp.springboot.learningjava.dto.ResponseDTO;

@Component
public class Common {

    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        return userService.login(user, password);
    }
}