
package com.example.securebanking.repository;

import com.example.securebanking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdAndCustomerId(Long accountId, Long customerId);
    boolean existsByAccountNumber(String accountNumber);
}
