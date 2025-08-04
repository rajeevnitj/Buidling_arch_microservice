package com.banking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Customer Data Transfer Object")
public class CustomerDto {
    @Schema(description = "Customer's first name", example = "John")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "Customer's last name", example = "Doe")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "Customer's email address", example = "john.doe@email.com")
    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Customer's phone number", example = "+1234567890")
    @NotBlank(message = "Phone is required")
    private String phone;

    @Schema(description = "Customer's address", example = "123 Main St, City, State")
    @NotBlank(message = "Address is required")
    private String address;

    public CustomerDto() {}

    public CustomerDto(String firstName, String lastName, String email, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}