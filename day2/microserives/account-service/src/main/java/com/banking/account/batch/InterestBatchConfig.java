package com.banking.account.batch;

import com.banking.account.entity.Account;
import com.banking.account.repository.AccountRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class InterestBatchConfig {

	@Autowired
	private AccountRepository accountRepository;

	@Bean
	public ItemReader<Account> interestReader() {
		RepositoryItemReader<Account> reader = new RepositoryItemReader<>();
		reader.setRepository(accountRepository);
		reader.setMethodName("findSavingsAccountsForInterest");
		LocalDateTime now = LocalDateTime.now().minusMonths(3);
		reader.setArguments(List.of(now));
//		reader.setArguments(LocalDateTime.now().minusMonths(3));

		Map<String, Sort.Direction> sorts = new HashMap<>();
		sorts.put("id", Sort.Direction.ASC);
		reader.setSort(sorts);

		return reader;
	}

	@Bean
	public ItemProcessor<Account, Account> interestProcessor() {
		return account -> {
			BigDecimal quarterlyRate = account.getInterestRate().divide(new BigDecimal("400"), 4, RoundingMode.HALF_UP);
			BigDecimal interest = account.getBalance().multiply(quarterlyRate);
			account.setBalance(account.getBalance().add(interest));
			account.setLastInterestApplied(LocalDateTime.now());
			return account;
		};
	}

	@Bean
	public ItemWriter<Account> interestWriter() {
		RepositoryItemWriter<Account> writer = new RepositoryItemWriter<>();
		writer.setRepository(accountRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step interestStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("interestStep", jobRepository).<Account, Account>chunk(100, transactionManager)
				.reader(interestReader()).processor(interestProcessor()).writer(interestWriter()).build();
	}

	@Bean
	public Job interestJob(JobRepository jobRepository, Step interestStep) {
		return new JobBuilder("interestJob", jobRepository).start(interestStep).build();
	}
}