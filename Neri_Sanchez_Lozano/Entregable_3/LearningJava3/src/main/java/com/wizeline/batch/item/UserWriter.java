package com.wizeline.batch.item;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class UserWriter implements ItemWriter<String> {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserWriter.class);
	 
	 @Override
	 public void write(List<? extends String> list) throws Exception {
	     for (String data: list) {
	         log.info("UserWriter: Writing data: " + data);
	     }
	     log.info("UserWriter: Writing data completed!!");
	 }
	}
