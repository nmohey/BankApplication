package com.sprints.BankApplication.repository;

import com.sprints.BankApplication.model.BankAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByAccountType(String type);

    List<BankAccount> findByBalanceGreaterThan(BigDecimal amount);

    @Query("SELECT a FROM BankAccount a WHERE a.balance BETWEEN :min AND :max")
    List<BankAccount> findAccountsInRange(Double min, Double max);

    @Modifying
    @Transactional
    @Query("UPDATE BankAccount b SET b.balance = :balance WHERE b.id = :accountId")
    int updateBalance(@Param("accountId") Long accountId, @Param("balance") Double balance);
}
