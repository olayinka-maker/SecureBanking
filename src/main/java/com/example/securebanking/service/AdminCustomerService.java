package com.example.securebanking.service;

import com.example.securebanking.dto.CustomerResponse;
import com.example.securebanking.repository.CustomerRespository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCustomerService {

    private final CustomerRespository customerRespository;

    public AdminCustomerService(CustomerRespository customerRespository) {
        this.customerRespository = customerRespository;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<CustomerResponse> getAllCustomers() {

        return customerRespository.findAll()
                .stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getRole()
                ))
                .toList();
    }
}