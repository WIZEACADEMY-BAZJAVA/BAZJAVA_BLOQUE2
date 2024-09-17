package com.wizeline.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.wizeline.bean.BankAccountBean;
import com.wizeline.enums.Country;
import com.wizeline.model.BankAccountDTO;
import com.wizeline.repository.BankingAccountRepository;
import com.wizeline.utils.Utils;

@Service
public class BankAccountServiceImpl implements BankAccountService{
	
	private static final Logger log = Logger.getLogger(BankAccountServiceImpl.class.getName());
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	BankingAccountRepository bankAccountRepository;

	@Override
	public BankAccountDTO getAccountDetails(String user, String lastUsage) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate usage = LocalDate.parse(lastUsage, dateFormatter);
		return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
	}

	private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
	    bankAccountDTO.setAccountNumber(Utils.randomAcountNumber());
	    bankAccountDTO.setAccountName("Dummy Account".concat(Utils.randomInt()));
	    bankAccountDTO.setUser(user);
	    bankAccountDTO.setAccountBalance(Utils.randomBalance());
	    bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
	    bankAccountDTO.setCountry(Utils.getCountry(country));
	    bankAccountDTO.setAccountActive(true);
	    bankAccountDTO.setLastUsage(lastUsage);
	    return bankAccountDTO;
	}

	@Override
	public List<BankAccountDTO> getAccounts() {
		List<BankAccountDTO> accountDTOList = new ArrayList<>();	    
	    mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUser()).forEach(
	            (user) -> {
	            	log.info("User stored in bankAccountCollection " + user );
	            });
	    
		return accountDTOList;
	}

	@Override
	public void deleteAccounts() {
		bankAccountRepository.deleteAll();
	}

	@Override
	public List<BankAccountDTO> getAccountByUser(String user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user").is(user));
		return mongoTemplate.find(query, BankAccountDTO.class);
	}

	@Override
	public void postAccount(BankAccountDTO account) {
	    mongoTemplate.save(account);
		
	}

	@Override
	public void updateAccount(BankAccountDTO account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putAccount(BankAccountDTO account) {
		mongoTemplate.save(account);
		
	}

	@Override
	public BankAccountDTO postAccount(BankAccountBean account) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO = mongoTemplate.save(buildBankAccount(account.getUser(), account.isActive(), account.getCountry(), LocalDateTime.now()));
		return bankAccountDTO;
	}

	@Override
	public long putAccount(BankAccountBean account) {
		Query query = new Query();
		UpdateResult result;
		query.addCriteria(Criteria.where("user").is(account.getUser()));
		result =  mongoTemplate.updateMulti(query, Update.update("country", 
				Utils.getCountry(account.getCountry())), 
				BankAccountDTO.class);
		return result.getMatchedCount();
		
	}

	
	

}
