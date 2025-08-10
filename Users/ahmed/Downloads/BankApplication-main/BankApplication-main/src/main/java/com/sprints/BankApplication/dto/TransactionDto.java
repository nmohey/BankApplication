package com.sprints.BankApplication.dto;

import java.math.BigDecimal;

public class TransactionDto {

    private String type;
    private BigDecimal amount;
    private Long customer_id;
    private Long account_id;

    public TransactionDto() {
    }

    public TransactionDto(String type, BigDecimal amount, Long customer_id, Long account_id) {
        this.type = type;
        this.amount = amount;
        this.customer_id = customer_id;
        this.account_id = account_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }
}
