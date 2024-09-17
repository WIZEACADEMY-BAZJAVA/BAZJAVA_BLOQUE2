package com.wizeline.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	BankAccountJob bankAccountJob;
	
	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
	
	@Scheduled(fixedRate = 15000)
	 public void scheduledByFixedRate() throws Exception {
	     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	     log.info("Batch job starting");
	     JobParameters jobParameters = new JobParametersBuilder()
	             .addString("launchDate", format.format(Calendar.getInstance().getTime()))
	             .addString("project", "LearningJava")
	             .toJobParameters();
	     jobLauncher.run(bankAccountJob.bankAccountsBackupJob(), jobParameters);
	     log.info("Batch job executed successfully");
	 }

}
