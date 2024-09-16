package com.superapp.springboot.learningjava.dao;

public interface UserDAO {

    String createUser(String user, String password);
    String login(String user, String password);

}
