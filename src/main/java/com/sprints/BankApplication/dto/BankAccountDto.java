package com.sprints.BankApplication.dto;

import java.math.BigDecimal;

public class BankAccountDto {

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private Long customer_id;

    public BankAccountDto() {
    }

    public BankAccountDto(String accountNumber, String accountType, BigDecimal balance, Long customer_id) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customer_id = customer_id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }
}
