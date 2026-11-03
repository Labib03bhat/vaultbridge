package com.banking.management.service;

import com.banking.management.dto.request.CustomerRequest;
import com.banking.management.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest customerRequest);

}