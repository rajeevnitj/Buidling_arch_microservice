package com.banking.customer.client;

import com.banking.customer.config.FeignConfig;
import com.banking.customer.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "account-service", configuration = FeignConfig.class)
public interface AccountServiceClient {
    
    @GetMapping("/api/accounts/customer/{customerId}")
    List<AccountDto> getAccountsByCustomerId(@PathVariable("customerId") Long customerId);
}