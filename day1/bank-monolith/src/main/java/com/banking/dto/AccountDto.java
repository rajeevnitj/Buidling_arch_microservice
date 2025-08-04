package com.banking.dto;

import com.banking.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Account Data Transfer Object")
public class AccountDto {
    @Schema(description = "Type of account", example = "SAVINGS")
    @NotNull(message = "Account type is required")
    private Account.AccountType accountType;

    @Schema(description = "ID of the customer who owns this account", example = "1")
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    public AccountDto() {}

    public AccountDto(Account.AccountType accountType, Long customerId) {
        this.accountType = accountType;
        this.customerId = customerId;
    }

    // Getters and Setters
    public Account.AccountType getAccountType() { return accountType; }
    public void setAccountType(Account.AccountType accountType) { this.accountType = accountType; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
}