package com.banking.management.dto.request;

import com.banking.management.enums.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class AccountRequest {

    @NotNull(message = "Customer ID is required.")
    private Long customerId;

    @NotNull(message = "Account type is required.")
    private AccountType accountType;

    @NotNull(message = "Initial deposit is required.")
    @DecimalMin(value = "0.01",
            message = "Initial deposit must be greater than zero.")
    private BigDecimal initialDeposit;

    public AccountRequest() {
    }

    public AccountRequest(Long customerId, AccountType accountType,
                          BigDecimal initialDeposit) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(BigDecimal initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
}