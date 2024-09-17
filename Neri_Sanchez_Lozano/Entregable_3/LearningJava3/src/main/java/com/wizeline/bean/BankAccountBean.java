package com.wizeline.bean;

import com.wizeline.enums.Country;

public class BankAccountBean {
	
	private String user;
	private boolean isActive;
	private Country country;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	

}
