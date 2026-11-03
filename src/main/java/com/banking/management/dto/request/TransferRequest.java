package com.banking.management.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TransferRequest {

    @NotBlank(message = "Source account number is required.")
    @Size(max = 20, message = "Invalid source account number.")
    private String sourceAccountNumber;

    @NotBlank(message = "Destination account number is required.")
    @Size(max = 20, message = "Invalid destination account number.")
    private String destinationAccountNumber;

    @NotNull(message = "Transfer amount is required.")
    @DecimalMin(value = "0.01",
            message = "Transfer amount must be greater than zero.")
    private BigDecimal amount;

    public TransferRequest() {
    }

    public TransferRequest(String sourceAccountNumber,
                           String destinationAccountNumber,
                           BigDecimal amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}