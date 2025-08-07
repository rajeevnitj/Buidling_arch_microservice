package com.banking.customer.repository;

import com.banking.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findByEmail_Success() {
        // Given
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPassword("password");
        customer.setPhone("1234567890");
        customer.setAddress("123 Main St");
        customer.setRole(Customer.Role.CUSTOMER);

        entityManager.persistAndFlush(customer);

        // When
        Optional<Customer> found = customerRepository.findByEmail("test@example.com");

        // Then
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void findByEmail_NotFound() {
        // When
        Optional<Customer> found = customerRepository.findByEmail("nonexistent@example.com");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void save_Success() {
        // Given
        Customer customer = new Customer();
        customer.setEmail("new@example.com");
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        customer.setPassword("password123");
        customer.setPhone("0987654321");
        customer.setAddress("456 Oak St");
        customer.setRole(Customer.Role.CUSTOMER);

        // When
        Customer saved = customerRepository.save(customer);

        // Then
        assertNotNull(saved.getId());
        assertEquals("new@example.com", saved.getEmail());
        assertEquals("Jane", saved.getFirstName());
    }
}