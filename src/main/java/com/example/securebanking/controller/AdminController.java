package com.example.securebanking.controller;

import com.example.securebanking.dto.CustomerResponse;
import com.example.securebanking.service.AdminCustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminCustomerService adminCustomerService;

    public AdminController(AdminCustomerService adminCustomerService) {
        this.adminCustomerService = adminCustomerService;
    }

    @GetMapping("/customers")
    public List<CustomerResponse> getAllCustomers() {
        return adminCustomerService.getAllCustomers();
    }
}