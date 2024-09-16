package com.superapp.springboot.learningjava.bo;

import com.superapp.springboot.learningjava.dto.ResponseDTO;

public interface UserBO {

    ResponseDTO createUser(String user, String password);
    ResponseDTO login(String user, String password);

}
