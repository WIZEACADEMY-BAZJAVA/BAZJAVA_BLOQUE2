package com.superapp.springboot.learningjava.service;

import java.util.List;

import com.superapp.springboot.learningjava.dto.BankAccountDTO;

public interface BankAccountService {

	List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);
    
}
