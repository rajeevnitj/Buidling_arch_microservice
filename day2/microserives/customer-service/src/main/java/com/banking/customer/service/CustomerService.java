package com.banking.customer.service;

import com.banking.customer.dto.CustomerDto;
import com.banking.customer.entity.Customer;
import com.banking.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public CustomerDto authenticateCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        if (passwordEncoder.matches(password, customer.getPassword())) {
            return convertToDto(customer);
        }
        throw new RuntimeException("Invalid credentials");
    }
    
    public CustomerDto createCustomer(String email, String firstName, String lastName, 
                                    String password, String phone, String address) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setRole(Customer.Role.CUSTOMER);
        
        Customer saved = customerRepository.save(customer);
        return convertToDto(saved);
    }
    
    public Optional<CustomerDto> getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::convertToDto);
    }
    
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public CustomerDto updateCustomer(Long id, String firstName, String lastName, String phone, String address) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setAddress(address);
        
        Customer updated = customerRepository.save(customer);
        return convertToDto(updated);
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    
    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setRole(customer.getRole().toString());
        dto.setCreatedAt(customer.getCreatedAt());
        return dto;
    }
}