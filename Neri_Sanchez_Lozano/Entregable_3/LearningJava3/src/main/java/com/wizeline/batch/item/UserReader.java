package com.wizeline.batch.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

public class UserReader implements ItemReader<String> {
	
	private static final Logger log = LoggerFactory.getLogger(UserReader.class);
	 
	 private String[] stringArray = {"Phoebe Buffay", "Rachel Green", "Monica Geller", "Chandler Bing", "Ross Geller", "Joey Tribbiani"};

	 private int index = 0;

	 @Override
	 public String read() throws Exception {
	     if (index >= stringArray.length) {
	         return null;
	     }
	     String data = index + " " + stringArray[index];
	     index++;
	     log.info("UserReader: Reading data: {}", data);
	     return data;
	 }
	}
