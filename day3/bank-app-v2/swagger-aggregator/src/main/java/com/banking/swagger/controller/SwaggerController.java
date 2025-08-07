package com.banking.swagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SwaggerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/v3/api-docs/customer")
    public Object getCustomerServiceDocs() {
        return restTemplate.getForObject("http://localhost:8081/v3/api-docs", Object.class);
    }

    @GetMapping("/v3/api-docs/account")
    public Object getAccountServiceDocs() {
        return restTemplate.getForObject("http://localhost:8082/v3/api-docs", Object.class);
    }

    @GetMapping("/v3/api-docs/transaction")
    public Object getTransactionServiceDocs() {
        return restTemplate.getForObject("http://localhost:8083/v3/api-docs", Object.class);
    }
}