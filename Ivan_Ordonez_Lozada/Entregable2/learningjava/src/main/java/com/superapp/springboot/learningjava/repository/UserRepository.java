package com.superapp.springboot.learningjava.repository;

public interface UserRepository {

	public String createUser(String user, String password);
	public String login (String user, String password);
	   
}
