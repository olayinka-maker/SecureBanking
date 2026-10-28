
package com.example.securebanking.security;

import com.example.securebanking.entity.Customer;
import com.example.securebanking.repository.CustomerRespository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRespository customerRepository;

    public CustomerDetailsService(CustomerRespository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid credentials")
                );

        return new CustomerUserDetails(customer);
    }
}
