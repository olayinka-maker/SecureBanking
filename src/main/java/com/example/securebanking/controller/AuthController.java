
package com.example.securebanking.controller;

import com.example.securebanking.dto.CustomerResponse;
import com.example.securebanking.dto.RegisterRequest;
import com.example.securebanking.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }
}
