package com.banking.management.controller;

import com.banking.management.dto.request.AccountRequest;
import com.banking.management.dto.response.AccountResponse;
import com.banking.management.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountRequest accountRequest) {

        AccountResponse response =
                accountService.createAccount(accountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                accountService.getAccountByNumber(accountNumber)
        );
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {

        return ResponseEntity.ok(
                accountService.deposit(accountNumber, amount)
        );
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable String accountNumber,
            @RequestParam BigDecimal amount) {

        return ResponseEntity.ok(
                accountService.withdraw(accountNumber, amount)
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam String sourceAccountNumber,
            @RequestParam String destinationAccountNumber,
            @RequestParam BigDecimal amount) {

        accountService.transfer(
                sourceAccountNumber,
                destinationAccountNumber,
                amount
        );

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{accountNumber}/freeze")
    public ResponseEntity<AccountResponse> freezeAccount(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                accountService.freezeAccount(accountNumber)
        );
    }

    @PatchMapping("/{accountNumber}/activate")
    public ResponseEntity<AccountResponse> activateAccount(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                accountService.activateAccount(accountNumber)
        );
    }
}