
package com.example.securebanking.dto;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String role
) {}
