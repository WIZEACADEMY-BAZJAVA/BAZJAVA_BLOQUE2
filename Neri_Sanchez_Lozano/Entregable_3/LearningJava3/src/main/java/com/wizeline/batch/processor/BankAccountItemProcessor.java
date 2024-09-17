package com.wizeline.batch.processor;

import org.jboss.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.wizeline.model.BankAccountDTO;

public class BankAccountItemProcessor implements ItemProcessor<BankAccountDTO, String> {
	
	
	private static final Logger log = Logger.getLogger(BankAccountItemProcessor.class);

	@Override
	public String process(BankAccountDTO item) throws Exception {
		String accountType = item.getAccountType().toString();
		String accounts = "Country" + item.getCountry() + " Account Name: " + item.getAccountName()
		+ " Account Type: " + item.getAccountType() + " Account Balance: " + item.getAccountBalance()
		+ " Name: " + item.getUser();
		log.info("Converting {} into {} " + item + " " + accounts);
		return null;
	}
	
	

}
