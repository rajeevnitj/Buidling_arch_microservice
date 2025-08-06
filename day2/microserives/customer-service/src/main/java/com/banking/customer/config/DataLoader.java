package com.banking.customer.config;

import com.banking.customer.entity.Customer;
import com.banking.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() == 0) {
            for (int i = 1; i <= 20; i++) {
                Customer customer = new Customer();
                customer.setFirstName("Customer" + i);
                customer.setLastName("User" + i);
                customer.setEmail("customer" + i + "@email.com");
                customer.setPassword(passwordEncoder.encode("password123"));
                customer.setPhone("+1234567" + String.format("%03d", i));
                customer.setAddress(i + " Main Street, City " + i);
                customerRepository.save(customer);
            }
        }
    }
}