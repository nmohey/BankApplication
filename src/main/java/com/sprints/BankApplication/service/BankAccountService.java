package com.sprints.BankApplication.service;

import com.sprints.BankApplication.dto.BankAccountDto;
import com.sprints.BankApplication.model.BankAccount;
import com.sprints.BankApplication.model.Customer;
import com.sprints.BankApplication.repository.BankAccountRepository;
import com.sprints.BankApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    private BankAccountDto mapToDto(BankAccount bankAccount) {
        if (bankAccount == null) return null;
        BankAccountDto dto = new BankAccountDto();
        dto.setAccountNumber(bankAccount.getAccountNumber());
        dto.setAccountType(bankAccount.getAccountType());
        dto.setBalance(bankAccount.getBalance());
        dto.setCustomer_id(bankAccount.getCustomer() != null ? bankAccount.getCustomer().getId() : null);
        return dto;
    }


    public BankAccountDto createBankAccount(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setAccountType(bankAccountDto.getAccountType());
        bankAccount.setBalance(bankAccountDto.getBalance());

        Optional<Customer> customerOptional = customerRepository.findById(bankAccountDto.getCustomer_id());
        customerOptional.ifPresent(bankAccount::setCustomer);

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return mapToDto(savedAccount);
    }

    public List<BankAccountDto> getAllBankAccounts() {
        return bankAccountRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BankAccountDto getBankAccountById(Long id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
        return bankAccount.map(this::mapToDto).orElse(null);
    }

    public BankAccountDto updateBankAccount(Long id, BankAccountDto bankAccountDto) {

        Optional<BankAccount> existingAccountOpt = bankAccountRepository.findById(id);

        if (existingAccountOpt.isPresent()) {
            BankAccount existingAccount = existingAccountOpt.get();

            existingAccount.setAccountNumber(bankAccountDto.getAccountNumber());

            existingAccount.setAccountType(bankAccountDto.getAccountType());
            existingAccount.setBalance(bankAccountDto.getBalance());

            Optional<Customer> customerOptional = customerRepository.findById(bankAccountDto.getCustomer_id());
            customerOptional.ifPresent(existingAccount::setCustomer);

            BankAccount updatedAccount = bankAccountRepository.save(existingAccount);
            return mapToDto(updatedAccount);
        }
        return null;
    }

    public boolean deleteBankAccount(Long id) {
        if (bankAccountRepository.existsById(id)) {
            bankAccountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BankAccountDto> findByAccountType(String type) {
        return bankAccountRepository.findByAccountType(type)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<BankAccountDto> findByBalanceGreaterThan(BigDecimal amount) {
        return bankAccountRepository.findByBalanceGreaterThan(amount)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<BankAccountDto> findAccountsInRange(Double min, Double max) {
        return bankAccountRepository.findAccountsInRange(min, max)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }


    public String transferMoney(Long senderAccountId, Long receiverAccountId, BigDecimal amount) {
        Optional<BankAccount> senderOpt = bankAccountRepository.findById(senderAccountId);
        Optional<BankAccount> receiverOpt = bankAccountRepository.findById(receiverAccountId);

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            throw new RuntimeException("Sender or receiver account not found");
        }

        BankAccount sender = senderOpt.get();
        BankAccount receiver = receiverOpt.get();

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in sender's account");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        bankAccountRepository.save(sender);
        bankAccountRepository.save(receiver);

        return "Transfer successful";
    }

    public void changeAccountBalance(Long accountId, Double newBalance) {
        bankAccountRepository.updateBalance(accountId, newBalance);
    }
}
