package com.banking.customer.service;

import com.banking.customer.dto.CustomerDto;
import com.banking.customer.entity.Customer;
import com.banking.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPassword("encodedPassword");
        customer.setRole(Customer.Role.CUSTOMER);
    }

    @Test
    void authenticateCustomer_Success() {
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        CustomerDto result = customerService.authenticateCustomer("test@example.com", "password");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void authenticateCustomer_InvalidCredentials() {
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> 
            customerService.authenticateCustomer("test@example.com", "wrongPassword"));
    }

    @Test
    void createCustomer_Success() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDto result = customerService.createCustomer("test@example.com", "John", "Doe", 
                                                           "password", "1234567890", "123 Main St");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void getCustomerById_Found() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<CustomerDto> result = customerService.getCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void getCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CustomerDto> result = customerService.getCustomerById(1L);

        assertFalse(result.isPresent());
    }
}