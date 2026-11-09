
package com.example.securebanking.controller;

import com.example.securebanking.entity.Account;
import com.example.securebanking.entity.Customer;
import com.example.securebanking.repository.CustomerRespository;
import com.example.securebanking.security.CustomerUserDetails;
import com.example.securebanking.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CustomerRespository customerRespository;

    public AccountController(AccountService accountService, CustomerRespository customerRespository) {
        this.accountService = accountService;
        this.customerRespository = customerRespository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createAccount(Authentication authentication) {

        CustomerUserDetails user = (CustomerUserDetails) authentication.getPrincipal();

        Long customerId = user.getCustomerId();

        Customer customer = customerRespository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalStateException("Authenticated customer not found")
                );

        Account account = accountService.createAccount(customer);

        return Map.of(
                "message", "Account created successfully",
                "accountId", account.getId(),
                "accountNumber", account.getAccountNumber(),
                "balance", account.getBalance()
        );
    }

    @GetMapping("/{accountId}")
    public Map<String, Object> getMyAccount(
            @PathVariable Long accountId,
            Authentication authentication) {

        CustomerUserDetails user =
                (CustomerUserDetails) authentication.getPrincipal();

        Long customerId = user.getCustomerId();

        Account account = accountService.getCustomerAccount(
                accountId,
                customerId
        );

        return Map.of(
                "accountId", account.getId(),
                "accountNumber", account.getAccountNumber(),
                "balance", account.getBalance()
        );
    }
}
