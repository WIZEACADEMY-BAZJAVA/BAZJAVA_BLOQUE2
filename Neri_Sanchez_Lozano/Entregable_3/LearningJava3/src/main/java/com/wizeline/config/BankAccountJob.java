package com.wizeline.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.wizeline.batch.item.BatchJobCompletionListener;
import com.wizeline.batch.processor.BankAccountItemProcessor;
import com.wizeline.model.BankAccountDTO;

@Configuration
public class BankAccountJob {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job bankAccountsBackupJob(){
		return jobBuilderFactory.get("bankAccountsBackupJob")
				.start(bankAccountsBackupStep(stepBuilderFactory))
				.listener(jobExecutionListener())
				.build();
				
	}
	
	@Bean
	public Step bankAccountsBackupStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("bankAccountsBackupStep")
				.<BankAccountDTO, String>chunk(5).reader(bankAccountsReader())
				.processor(bankAccountItemProcessor()).writer(bankAccountsWriter())
				.build();
		
	}
	
	
	@Bean
	public FlatFileItemReader<BankAccountDTO> bankAccountsReader(){
		return new FlatFileItemReaderBuilder<BankAccountDTO>()
				.name("bankAccountsReader")
				.resource(new ClassPathResource("csv/accounts.csv"))
				.delimited().names(new String[] {"country", "accountName", "accountType", "accountBalance", "userName"})
				.targetType(BankAccountDTO.class)
				.build();
	}
	
	
	@Bean
	public FlatFileItemWriter<String> bankAccountsWriter(){
		
		return new FlatFileItemWriterBuilder<String>()
				.name("bankAccountsWriter")
				.resource(new FileSystemResource("target/test-outputs/bankAccountsBackup.txt"))
				.lineAggregator(new PassThroughLineAggregator<>())
				.build();
		
	}
	
	@Bean
	public BankAccountItemProcessor bankAccountItemProcessor() {
		return new BankAccountItemProcessor();
	}
	
	@Bean
	public JobExecutionListener jobExecutionListener() {
		return new BatchJobCompletionListener();
	}
	

}
