package com.superapp.springboot.learningjava.repository.impl;

import com.superapp.springboot.learningjava.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.logging.Logger;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class.getName());

    @Override
    public String createUser(String user, String password){
        createFile();
        LOGGER.info("Inicia procesamiento en capa de acceso de datos");
        LOGGER.info("Inicia proceso de alta de usuario en BD...");

        writeFile(user, password);

        LOGGER.info("Alta exitosa");
        return "success";
    }
    @Override
    public String login(String user, String password){
        createFile();
        LOGGER.info("Inicia procesamiento en capa de acceso de datos");
        LOGGER.info("Inicia proceso de login...");

        if("success".equals(readFile(user, password))){
            LOGGER.info("login exitoso");
            return "success";
        }else{
            return "usuario o password incorrecto";
        }
    }

    private void createFile(){
        try{
            File myObj = new File ("file.txt");
            if (myObj.createNewFile()){
                LOGGER.info("File created: " + myObj.getName());
            } else {
                LOGGER.info("File alreade exist");

            }
        }catch (IOException e){
            LOGGER.info("An error ocurred.");
            e.printStackTrace();
        }
    }

    private void writeFile(String user, String password){
        try{
            File file = new File("file.txt");
            if(file.createNewFile()){
                LOGGER.info("File created: " + file.getName());
            }else{
                LOGGER.info("File already exists.");
            }
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bw = new BufferedWriter(fileWritter);

            bw.write( user+", "+password);
            bw.newLine();
            bw.close();
            LOGGER.info("Successfully wrote to the file");
        }catch (IOException e){
            LOGGER.info("An error ocurred.");
            e.printStackTrace();
        }
    }

    private String readFile(String user, String password){
        String result = "fail";
        try{
            File file = new File("file.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine()) != null){
                if(line.contains(user) && line.contains(password)){
                    result = "success";
                }
            }
            br.close();
        }catch (IOException e) {
            LOGGER.info("An error ocurred.");
            e.printStackTrace();
        }
        return result;
    }

}
