package com.wizeline.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wizeline.batch.item.BatchJobCompletionListener;
import com.wizeline.batch.item.UserProcessor;
import com.wizeline.batch.item.UserReader;
import com.wizeline.batch.item.UserWriter;

@Configuration
public class UserJob {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;

	 @Bean
	 public Job printUsersJob() {
	     return jobBuilderFactory.get("printUsersJob")
	             .incrementer(new RunIdIncrementer())
	             .flow(printUserStep())
	             .end().listener(new BatchJobCompletionListener())
	             .build();
	
	 }
	
	 @Bean
	 public Step printUserStep() {
	     return stepBuilderFactory.get("printUserStep")
	             .<String, String>chunk(3)
	             .reader(new UserReader())
	             .processor(new UserProcessor())
	             .writer(new UserWriter())
	             .build();
	 }
}
