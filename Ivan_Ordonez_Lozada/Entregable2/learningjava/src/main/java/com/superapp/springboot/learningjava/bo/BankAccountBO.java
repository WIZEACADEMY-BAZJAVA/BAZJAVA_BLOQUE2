package com.superapp.springboot.learningjava.bo;

import com.superapp.springboot.learningjava.dto.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {

    BankAccountDTO getAccountDetails(String user, String lastUsage);
    List<BankAccountDTO> getAccounts();

}
