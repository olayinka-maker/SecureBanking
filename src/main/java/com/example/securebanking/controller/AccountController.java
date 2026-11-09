
package com.example.securebanking.controller;

import com.example.securebanking.dto.DepositRequest;
import com.example.securebanking.dto.WithdrawRequest;
import com.example.securebanking.entity.Account;
import com.example.securebanking.entity.Customer;
import com.example.securebanking.repository.CustomerRespository;
import com.example.securebanking.security.CustomerUserDetails;
import com.example.securebanking.service.AccountService;

import jakarta.validation.Valid;
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

    @PostMapping("/{accountId}/deposit")
    public Map<String, Object> deposit(@PathVariable Long accountId, @Valid @RequestBody DepositRequest request,
                                       Authentication authentication) {

        CustomerUserDetails user = (CustomerUserDetails) authentication.getPrincipal();

        Account account = accountService.deposit(
                accountId,
                user.getCustomerId(),
                request.amount()
        );

        return Map.of(
                "message", "Deposit successful",
                "accountId", account.getId(),
                "accountNumber", account.getAccountNumber(),
                "balance", account.getBalance()
        );


    }


    @PostMapping("/{accountId}/withdraw")
    public Map<String, Object> withdraw(@PathVariable Long accountId, @Valid @RequestBody WithdrawRequest request,
                                        Authentication authentication){

        CustomerUserDetails user = (CustomerUserDetails) authentication.getPrincipal();

        Account account = accountService.withdraw(
                accountId,
                user.getCustomerId(),
                request.amount()
        );

        return Map.of(
                "message", "withdrawal successful",
                "accountId", account.getId(),
                "accountNumber", account.getAccountNumber(),
                "balance", account.getBalance()
        );


    }
}
