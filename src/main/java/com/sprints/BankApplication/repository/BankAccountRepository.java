package com.sprints.BankApplication.repository;

import com.sprints.BankApplication.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByAccountType(String type);

    List<BankAccount> findByBalanceGreaterThan(BigDecimal amount);

    @Query("SELECT a FROM BankAccount a WHERE a.balance BETWEEN :min AND :max")
    List<BankAccount> findAccountsInRange(Double min, Double max);
}
