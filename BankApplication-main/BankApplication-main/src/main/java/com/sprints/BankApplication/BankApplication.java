package com.sprints.BankApplication;
import com.sprints.BankApplication.dto.BankAccountDto;
import com.sprints.BankApplication.dto.CustomerDto;
import com.sprints.BankApplication.dto.TransactionDto;
import com.sprints.BankApplication.service.BankAccountService;
import com.sprints.BankApplication.service.CustomerService;
import com.sprints.BankApplication.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			CustomerService customerService,
			BankAccountService bankAccountService,
			TransactionService transactionService) {
		return args -> {
			CustomerDto cust1 = customerService.createCustomer(new CustomerDto(null, "Omar", "omar@example.com", "0123456789"));
			CustomerDto cust2 = customerService.createCustomer(new CustomerDto(null, "Ahmed", "ahmed@example.com", "0987654321"));

			BankAccountDto acc1 = bankAccountService.createBankAccount(
					new BankAccountDto(1, "A101", "Savings", new BigDecimal("1500.00"), cust1.getId()));

			BankAccountDto acc2 = bankAccountService.createBankAccount(
					new BankAccountDto(2, "A102", "Checking", new BigDecimal("2500.00"), cust2.getId()));

			transactionService.createTransaction(
					new TransactionDto("Deposit", new BigDecimal("1500.00"), cust1.getId(), acc1.getId()));

			transactionService.createTransaction(
					new TransactionDto("Withdrawal", new BigDecimal("200.00"), cust2.getId(), acc2.getId()));
		};
	}
}

