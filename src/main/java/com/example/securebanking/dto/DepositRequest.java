package com.example.securebanking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DepositRequest (

    @NotNull(message = "Amount is Required")
            @DecimalMin(
                    value = "0.01",
                    message = "Deposit amount must be greater than zero"
            )
    BigDecimal amount

){}