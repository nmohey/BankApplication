package com.sprints.BankApplication.repository;

import com.sprints.BankApplication.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountId(Integer accountId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Transaction t WHERE t.account.id = :accountId")
    int deleteByAccountId(@Param("accountId") Integer accountId);
}
