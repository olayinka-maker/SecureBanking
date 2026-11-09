
package com.example.securebanking.service;

import com.example.securebanking.entity.Account;
import com.example.securebanking.entity.Customer;
import com.example.securebanking.repository.AccountRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getCustomerAccount(Long accountId, Long customerId) {

        return accountRepository
                .findByIdAndCustomerId(accountId, customerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Account not found")
                );
    }

    public Account createAccount(Customer customer) {

        String accountNumber;

        do {
            accountNumber = generateAccountNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));

        Account account = new Account();

        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);

        return accountRepository.save(account);
    }

    private String generateAccountNumber() {

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            number.append(secureRandom.nextInt(10));
        }

        return number.toString();
    }
}
