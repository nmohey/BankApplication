package com.sprints.BankApplication.controller;

import com.sprints.BankApplication.dto.BankAccountDto;
import com.sprints.BankApplication.model.BankAccount;
import com.sprints.BankApplication.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // Create bank account
    @PostMapping
    public ResponseEntity<BankAccountDto> createAccount(@Valid @RequestBody BankAccountDto dto) {
        BankAccountDto created = bankAccountService.createBankAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    // Get account by ID
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getAccountById(@PathVariable Long id) {
        BankAccountDto account = bankAccountService.getBankAccountById(id);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    // Update account
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDto> updateAccount(@PathVariable Long id,
                                                        @Valid @RequestBody BankAccountDto dto) {
        BankAccountDto updated = bankAccountService.updateBankAccount(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Delete account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        boolean deleted = bankAccountService.deleteBankAccount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Search by account type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<BankAccountDto>> getByAccountType(@PathVariable String type) {
        return ResponseEntity.ok(bankAccountService.findByAccountType(type));
    }

    // Search accounts with balance greater than
    @GetMapping("/min-balance/{amount}")
    public ResponseEntity<List<BankAccountDto>> getByMinBalance(@PathVariable BigDecimal amount) {
        return ResponseEntity.ok(bankAccountService.findByBalanceGreaterThan(amount));
    }

    // Search accounts in range
    @GetMapping("/balance-range")
    public ResponseEntity<List<BankAccountDto>> getAccountsInRange(@RequestParam Double min,
                                                                   @RequestParam Double max) {
        return ResponseEntity.ok(bankAccountService.findAccountsInRange(min, max));
    }

    // Transfer money between accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long senderId,
                                           @RequestParam Long receiverId,
                                           @RequestParam BigDecimal amount) {
        String result = bankAccountService.transferMoney(senderId, receiverId, amount);
        return ResponseEntity.ok(result);
    }

    // Update account balance (custom @Modifying query)
    @PatchMapping("/{id}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable Long id, @RequestParam Double newBalance) {
        bankAccountService.changeAccountBalance(id, newBalance);
        return ResponseEntity.noContent().build();
    }
}
