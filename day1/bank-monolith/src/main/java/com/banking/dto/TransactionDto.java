package com.banking.dto;

import com.banking.entity.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "Transaction Data Transfer Object")
public class TransactionDto {
    @Schema(description = "Type of transaction", example = "DEPOSIT")
    @NotNull(message = "Transaction type is required")
    private Transaction.TransactionType type;

    @Schema(description = "Transaction amount", example = "100.50")
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Schema(description = "Transaction description", example = "Salary deposit")
    private String description;

    @Schema(description = "ID of the account for this transaction", example = "1")
    @NotNull(message = "Account ID is required")
    private Long accountId;

    public TransactionDto() {}

    public TransactionDto(Transaction.TransactionType type, BigDecimal amount, String description, Long accountId) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.accountId = accountId;
    }

    // Getters and Setters
    public Transaction.TransactionType getType() { return type; }
    public void setType(Transaction.TransactionType type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
}