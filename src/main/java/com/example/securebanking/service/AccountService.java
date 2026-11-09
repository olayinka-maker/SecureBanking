
package com.example.securebanking.service;

import com.example.securebanking.entity.Account;
import com.example.securebanking.entity.Customer;
import com.example.securebanking.repository.AccountRepository;

import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
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


    @Transactional
    public Account deposit(Long accountId,Long customerId, BigDecimal amount) {


        //i dey confirm the deposit amount
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit Amount must be greater than zero");
        }

        //check account ownership
        Account account = getCustomerAccount(accountId, customerId);


        BigDecimal newBalance = account.getBalance().add(amount);

        // 4. Update the account balance
        account.setBalance(newBalance);

        // 5. Save the updated account
        return accountRepository.save(account);
    }


    @Transactional
    public Account withdraw(Long accountId,Long customerId, BigDecimal amount) {

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 )  {
            throw new IllegalArgumentException("Withdrawal Amount must be greater than zero");
        }

        Account account = getCustomerAccount(accountId, customerId);

        // 3. Check sufficient balance
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException(
                    "Insufficient account balance"
            );
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);

        return accountRepository.save(account);

    }
}
