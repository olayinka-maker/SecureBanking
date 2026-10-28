package com.example.securebanking.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/api/public")
    public String publicEndpoint() {
        return "Public endpoint works";
    }

    @GetMapping("/api/private")
    public String privateEndpoint() {
        return "Private endpoint works";
    }
}
