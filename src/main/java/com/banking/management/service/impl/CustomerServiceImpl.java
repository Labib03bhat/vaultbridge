package com.banking.management.service.impl;

import com.banking.management.dto.request.CustomerRequest;
import com.banking.management.dto.response.CustomerResponse;
import com.banking.management.entity.Customer;
import com.banking.management.repository.CustomerRepository;
import com.banking.management.service.CustomerService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new IllegalArgumentException(
                    "A customer with this email already exists."
            );
        }

        Customer customer = new Customer(
                customerRequest.getFullName(),
                customerRequest.getEmail(),
                customerRequest.getPhoneNumber(),
                customerRequest.getAddress()
        );

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getFullName(),
                savedCustomer.getEmail(),
                savedCustomer.getPhoneNumber(),
                savedCustomer.getAddress(),
                savedCustomer.getCreatedAt()
        );
    }
}
