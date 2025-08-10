package com.sprints.BankApplication.service;

import com.sprints.BankApplication.dto.TransactionDto;
import com.sprints.BankApplication.model.Transaction;
import com.sprints.BankApplication.model.Customer;
import com.sprints.BankApplication.model.BankAccount;
import com.sprints.BankApplication.repository.TransactionRepository;
import com.sprints.BankApplication.repository.CustomerRepository;
import com.sprints.BankApplication.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionService(TransactionRepository transactionRepository, CustomerRepository customerRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setCustomer_id(transaction.getCustomer().getId());
        dto.setAccount_id(transaction.getAccount().getId());
        return dto;
    }

    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());

        Optional<Customer> customerOpt = customerRepository.findById(transactionDto.getCustomer_id());
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(transactionDto.getAccount_id());

        if (customerOpt.isPresent() && accountOpt.isPresent()) {
            transaction.setCustomer(customerOpt.get());
            transaction.setAccount(accountOpt.get());
            transactionRepository.save(transaction);
            return mapToDto(transaction);
        } else {
            throw new RuntimeException("Customer or Bank Account not found");
        }
    }

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return mapToDto(transaction);
    }

    public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setType(transactionDto.getType());
        transaction.setAmount(transactionDto.getAmount());

        Optional<Customer> customerOpt = customerRepository.findById(transactionDto.getCustomer_id());
        Optional<BankAccount> accountOpt = bankAccountRepository.findById(transactionDto.getAccount_id());

        if (customerOpt.isPresent() && accountOpt.isPresent()) {
            transaction.setCustomer(customerOpt.get());
            transaction.setAccount(accountOpt.get());
            transactionRepository.save(transaction);
            return mapToDto(transaction);
        } else {
            throw new RuntimeException("Customer or Bank Account not found");
        }
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public void deleteTransactionsByAccount(Long accountId) {
        transactionRepository.deleteByAccountId(accountId);
    }


}
