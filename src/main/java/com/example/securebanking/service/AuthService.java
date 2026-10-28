
package com.example.securebanking.service;

import com.example.securebanking.dto.CustomerResponse;
import com.example.securebanking.dto.RegisterRequest;
import com.example.securebanking.entity.Customer;

import com.example.securebanking.repository.CustomerRespository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final CustomerRespository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(CustomerRespository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CustomerResponse register(RegisterRequest request) {

        if (customerRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        String encodedPassword =
                passwordEncoder.encode(request.password());

        Customer customer = new Customer(
                request.name(),
                request.email(),
                encodedPassword,
                "CUSTOMER"
        );

        Customer savedCustomer =
                customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail(),
                savedCustomer.getRole()
        );
    }
}
