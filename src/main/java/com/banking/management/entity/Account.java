package com.banking.management.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.banking.management.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;


@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private boolean active;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account() {
    }

    public Account(  String accountNumber, Customer customer, BigDecimal balance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        this.accountType = accountType;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
