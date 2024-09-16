package com.superapp.springboot.learningjava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superapp.springboot.learningjava.dto.ErrorDTO;
import com.superapp.springboot.learningjava.dto.ResponseDTO;
import com.superapp.springboot.learningjava.repository.UserRepository;
import com.superapp.springboot.learningjava.repository.impl.UserRepositoryImpl;
import com.superapp.springboot.learningjava.service.UserService;
import static com.superapp.springboot.learningjava.utils.Utils.*;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password){
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if(validateNullValue(user) && validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);

        }else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001", "Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password){
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if(validateNullValue(user) && validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.login(user, password);
        }
        if("success".equals(result)){
            response.setCode("OK000");
            response.setStatus(result);
        }else{
            response.setCode("ER001");
            response.setErrors(new ErrorDTO("ER001", result));
            response.setStatus("fail");
        }
        return response;
    }


}