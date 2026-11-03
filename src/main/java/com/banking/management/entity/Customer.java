package com.banking.management.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Customer name cannot be empty")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    @Email(message = "Please provide a valid email address")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$",
            message = "Please provide a valid phone number")
    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;
    @Column(name = "address", length = 255)
    private String address;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Account> accounts = new ArrayList<>();


    public Customer() {
    }
    public Customer(String fullName, String email,
                    String phoneNumber, String address) {

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public void addAccount(Account account) {
        accounts.add(account);
        account.setCustomer(this);
    }

    public String getFullName() {
        return fullName;
    }
    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
