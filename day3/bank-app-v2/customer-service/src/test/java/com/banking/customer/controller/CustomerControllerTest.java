package com.banking.customer.controller;

import com.banking.customer.client.AccountServiceClient;
import com.banking.customer.dto.AccountDto;
import com.banking.customer.dto.CustomerDto;
import com.banking.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountServiceClient accountServiceClient;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_Success() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setEmail("test@example.com");
        customerDto.setFirstName("John");
        customerDto.setRole("CUSTOMER");

        when(customerService.authenticateCustomer("test@example.com", "password"))
                .thenReturn(customerDto);

        Map<String, String> request = Map.of(
                "email", "test@example.com",
                "password", "password"
        );

        mockMvc.perform(post("/api/customers/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }

    @Test
    void login_InvalidCredentials() throws Exception {
        when(customerService.authenticateCustomer("test@example.com", "wrongpassword"))
                .thenThrow(new RuntimeException("Invalid credentials"));

        Map<String, String> request = Map.of(
                "email", "test@example.com",
                "password", "wrongpassword"
        );

        mockMvc.perform(post("/api/customers/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }

    @Test
    void createCustomer_Success() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setEmail("new@example.com");
        customerDto.setFirstName("Jane");

        when(customerService.createCustomer(any(), any(), any(), any(), any(), any()))
                .thenReturn(customerDto);

        Map<String, String> request = Map.of(
                "email", "new@example.com",
                "firstName", "Jane",
                "lastName", "Smith",
                "password", "password123",
                "phone", "0987654321",
                "address", "456 Oak St"
        );

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new@example.com"))
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void getCustomer_Success() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setEmail("test@example.com");
        customerDto.setFirstName("John");

        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customerDto));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getAccountsByCustomerId_Success() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(1L);
        accountDto.setAccountNumber("ACC001");
        accountDto.setCustomerId(1L);

        List<AccountDto> accounts = Arrays.asList(accountDto);

        when(accountServiceClient.getAccountsByCustomerId(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/customers/1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("ACC001"))
                .andExpect(jsonPath("$[0].customerId").value(1));
    }
}