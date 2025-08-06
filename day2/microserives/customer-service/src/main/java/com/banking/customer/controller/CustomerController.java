package com.banking.customer.controller;

import com.banking.customer.dto.CustomerDto;
import com.banking.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping("/auth/login")
    @Operation(summary = "Authenticate customer")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        try {
            CustomerDto customer = customerService.authenticateCustomer(
                request.get("email"), 
                request.get("password")
            );
            Map<String, Object> response = Map.of(
                "customer", customer,
                "role", customer.getRole(),
                "token", "dummy-token-" + customer.getId()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
    
    @PostMapping
    @Operation(summary = "Create a new customer")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody Map<String, String> request) {
        CustomerDto customer = customerService.createCustomer(
            request.get("email"),
            request.get("firstName"),
            request.get("lastName"),
            request.get("password"),
            request.get("phone"),
            request.get("address")
        );
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody Map<String, String> request) {
        CustomerDto customer = customerService.updateCustomer(
            id,
            request.get("firstName"),
            request.get("lastName"),
            request.get("phone"),
            request.get("address")
        );
        return ResponseEntity.ok(customer);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }
}