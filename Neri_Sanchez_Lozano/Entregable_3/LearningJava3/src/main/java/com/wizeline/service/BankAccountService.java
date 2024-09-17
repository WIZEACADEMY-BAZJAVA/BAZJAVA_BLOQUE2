package com.wizeline.service;

import java.util.List;

import com.wizeline.bean.BankAccountBean;
import com.wizeline.model.BankAccountDTO;

public interface BankAccountService {
	
	BankAccountDTO getAccountDetails(String user, String lastUsage);
	
	List<BankAccountDTO> getAccounts();
	
	void deleteAccounts();
	
	void postAccount(BankAccountDTO account);
	
	void putAccount(BankAccountDTO account);
	
	void updateAccount(BankAccountDTO account);
	
	List<BankAccountDTO> getAccountByUser(String user);

	BankAccountDTO postAccount(BankAccountBean account);
	
	long putAccount(BankAccountBean account);

}
