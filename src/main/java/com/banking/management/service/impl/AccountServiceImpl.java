package com.banking.management.service.impl;

import com.banking.management.dto.request.AccountRequest;
import com.banking.management.dto.response.AccountResponse;
import com.banking.management.entity.Account;
import com.banking.management.entity.Customer;
import com.banking.management.repository.AccountRepository;
import com.banking.management.repository.CustomerRepository;
import com.banking.management.service.AccountService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            CustomerRepository customerRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest accountRequest) {

        Customer customer = customerRepository.findById(
                accountRequest.getCustomerId()
        ).orElseThrow(() ->
                new IllegalArgumentException("Customer not found."));

        validatePositiveAmount(accountRequest.getInitialDeposit());

        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(accountRequest.getAccountType());
        account.setBalance(accountRequest.getInitialDeposit());
        account.setActive(true);
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found."));

        return mapToResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse deposit(String accountNumber, BigDecimal amount) {

        validatePositiveAmount(amount);

        Account account = getAccountForUpdate(accountNumber);

        validateActiveAccount(account);

        account.setBalance(account.getBalance().add(amount));

        return mapToResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse withdraw(String accountNumber, BigDecimal amount) {

        validatePositiveAmount(amount);

        Account account = getAccountForUpdate(accountNumber);

        validateActiveAccount(account);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient account balance.");
        }

        account.setBalance(account.getBalance().subtract(amount));

        return mapToResponse(account);
    }

    @Override
    @Transactional
    public void transfer(
            String sourceAccountNumber,
            String destinationAccountNumber,
            BigDecimal amount) {

        validatePositiveAmount(amount);

        if (sourceAccountNumber.equals(destinationAccountNumber)) {
            throw new IllegalArgumentException(
                    "Source and destination accounts must be different.");
        }

        String firstAccount = sourceAccountNumber.compareTo(
                destinationAccountNumber) < 0
                ? sourceAccountNumber : destinationAccountNumber;

        String secondAccount = sourceAccountNumber.compareTo(
                destinationAccountNumber) < 0
                ? destinationAccountNumber : sourceAccountNumber;

        Account first = getAccountForUpdate(firstAccount);
        Account second = getAccountForUpdate(secondAccount);

        Account source = sourceAccountNumber.equals(firstAccount)
                ? first : second;

        Account destination = destinationAccountNumber.equals(firstAccount)
                ? first : second;

        validateActiveAccount(source);
        validateActiveAccount(destination);

        if (source.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient account balance.");
        }

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(destination);
    }

    @Override
    @Transactional
    public AccountResponse freezeAccount(String accountNumber) {

        Account account = getAccountForUpdate(accountNumber);

        if (!account.isActive()) {
            throw new IllegalArgumentException("Account is already frozen.");
        }

        account.setActive(false);

        return mapToResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse activateAccount(String accountNumber) {

        Account account = getAccountForUpdate(accountNumber);

        if (account.isActive()) {
            throw new IllegalArgumentException("Account is already active.");
        }

        account.setActive(true);

        return mapToResponse(account);
    }

    private Account getAccountForUpdate(String accountNumber) {

        return accountRepository.findByAccountNumberForUpdate(accountNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found."));
    }

    private void validateActiveAccount(Account account) {

        if (!account.isActive()) {
            throw new IllegalArgumentException(
                    "Account is frozen and cannot perform transactions.");
        }
    }

    private void validatePositiveAmount(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero.");
        }
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = "VA" +
                    UUID.randomUUID().toString()
                            .replace("-", "")
                            .substring(0, 16)
                            .toUpperCase();

        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    private AccountResponse mapToResponse(Account account) {

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getCreatedAt(),
                account.isActive(),
                account.getCustomer().getId()
        );
    }
}