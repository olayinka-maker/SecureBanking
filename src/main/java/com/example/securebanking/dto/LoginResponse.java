
package com.example.securebanking.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {}
