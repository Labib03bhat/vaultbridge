package com.banking.management.controller;

import com.banking.management.dto.request.CustomerRequest;
import com.banking.management.dto.response.CustomerResponse;
import com.banking.management.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest customerRequest) {

        CustomerResponse customerResponse =
                customerService.createCustomer(customerRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerResponse);
    }
}
