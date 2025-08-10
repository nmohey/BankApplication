package com.sprints.BankApplication.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransactionDto {

    @NotBlank(message = "Transaction type is required")
    @Size(max = 50, message = "Transaction type must not exceed 50 characters")
    private String type;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    @NotNull(message = "Customer ID is required")
    private Integer customer_id;
    @NotNull(message = "Account ID is required")
    private Integer account_id;

    public TransactionDto() {
    }

    public TransactionDto(String type, BigDecimal amount, Integer customer_id, Integer account_id) {
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

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }
}
