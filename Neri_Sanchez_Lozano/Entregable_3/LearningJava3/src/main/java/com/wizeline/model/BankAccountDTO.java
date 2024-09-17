package com.wizeline.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wizeline.enums.AccountType;

@Document
public class BankAccountDTO {
	 
    private long accountNumber;
    private String accountName;
    private String user;
    private double accountBalance;
    private AccountType accountType;
    private String country;
    private boolean accountActive;
    private LocalDateTime lastUsage;
    private LocalDateTime creationDate;
    
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@JsonProperty("AccountActive")
	public boolean isAccountActive() {
		return accountActive;
	}
	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
	}
	public LocalDateTime getLastUsage() {
		return lastUsage;
	}
	public void setLastUsage(LocalDateTime lastUsage) {
		this.lastUsage = lastUsage;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
        

}
