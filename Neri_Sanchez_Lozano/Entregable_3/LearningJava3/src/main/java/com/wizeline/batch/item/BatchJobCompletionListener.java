package com.wizeline.batch.item;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class BatchJobCompletionListener extends JobExecutionListenerSupport{
	
	private static final Logger log = LoggerFactory.getLogger(BatchJobCompletionListener.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Batch Job: '{}' with id: '{}' completed with status: '{}'",
					jobExecution.getJobInstance().getJobName(), jobExecution.getJobId(), jobExecution.getStatus());
		}
	}
	

}
