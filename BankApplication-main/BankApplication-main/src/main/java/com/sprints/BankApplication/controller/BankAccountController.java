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

    public BankAccountController(BankAccountService bankAccountService) {this.bankAccountService = bankAccountService;}

    @PostMapping
    public ResponseEntity<BankAccountDto> createAccount(@Valid @RequestBody BankAccountDto dto) {BankAccountDto created = bankAccountService.createBankAccount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {return ResponseEntity.ok(bankAccountService.getAllBankAccounts());}

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getAccountById(@PathVariable Integer id) {BankAccountDto account = bankAccountService.getBankAccountById(id);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDto> updateAccount(@PathVariable Integer id, @Valid @RequestBody BankAccountDto dto) {
        BankAccountDto updated = bankAccountService.updateBankAccount(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {boolean deleted = bankAccountService.deleteBankAccount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<BankAccountDto>> getByAccountType(@PathVariable String type) {
        return ResponseEntity.ok(bankAccountService.findByAccountType(type));
    }

    @GetMapping("/min-balance/{amount}")
    public ResponseEntity<List<BankAccountDto>> getByMinBalance(@PathVariable BigDecimal amount) {
        return ResponseEntity.ok(bankAccountService.findByBalanceGreaterThan(amount));
    }

    @GetMapping("/balance-range")
    public ResponseEntity<List<BankAccountDto>> getAccountsInRange(@RequestParam Double min,
                                                                   @RequestParam Double max) {
        return ResponseEntity.ok(bankAccountService.findAccountsInRange(min, max));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Integer senderId,
                                           @RequestParam Integer receiverId,
                                           @RequestParam BigDecimal amount) {
        String result = bankAccountService.transferMoney(senderId, receiverId, amount);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable Integer id, @RequestParam Double newBalance) {
        bankAccountService.changeAccountBalance(id, newBalance);
        return ResponseEntity.noContent().build();
    }
}
