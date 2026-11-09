package com.example.securebanking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WithdrawRequest(

        @NotNull(message = "Amount is required")
        @DecimalMin(
                value = "0.01",
                message = "Withdrawal amount must be greater than zero"
        )
        BigDecimal amount

) {
}