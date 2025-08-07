package com.banking.account.security;

import java.security.Principal;

public class CustomerPrincipal implements Principal {
    private final String email;
    private final Long customerId;
    private final String role;

    public CustomerPrincipal(String email, Long customerId, String role) {
        this.email = email;
        this.customerId = customerId;
        this.role = role;
    }

    @Override
    public String getName() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getRole() {
        return role;
    }
}