package com.banking.management.service;

import com.banking.management.dto.request.AccountRequest;
import com.banking.management.dto.response.AccountResponse;

import java.math.BigDecimal;

public interface AccountService {

    AccountResponse createAccount(AccountRequest accountRequest);

    AccountResponse getAccountByNumber(String accountNumber);

    AccountResponse deposit(String accountNumber, BigDecimal amount);

    AccountResponse withdraw(String accountNumber, BigDecimal amount);

    void transfer(
            String sourceAccountNumber,
            String destinationAccountNumber,
            BigDecimal amount
    );

    AccountResponse freezeAccount(String accountNumber);

    AccountResponse activateAccount(String accountNumber);
}