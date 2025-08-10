package com.sprints.BankApplication.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransactionDto {

    @NotBlank(message = "Transaction type is required")
    @Size(max = 50, message = "Transaction type must not exceed 50 characters")
    private String type;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    @NotNull(message = "Customer ID is required")
    private Long customer_id;
    @NotNull(message = "Account ID is required")
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
