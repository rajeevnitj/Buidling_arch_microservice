package com.banking.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class BankingController {
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        String token = (String) session.getAttribute("token");
        if (user == null || token == null) {
            return "redirect:/login";
        }
        
        try {
            Map<String, Object> userMap = (Map<String, Object>) user;
            Map<String, Object> customer = (Map<String, Object>) userMap.get("customer");
            Long customerId = ((Number) customer.get("id")).longValue();
            
            // Get accounts for the logged-in customer
            Object accounts = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/customers/" + customerId + "/accounts")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            
            model.addAttribute("currentUser", user);
            model.addAttribute("accounts", accounts);
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load dashboard: " + e.getMessage());
        }
        
        return "dashboard";
    }
    
    @GetMapping("/account/{accountNumber}/transactions")
    public String accountTransactions(@PathVariable String accountNumber, Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        String token = (String) session.getAttribute("token");
        if (user == null || token == null) {
            return "redirect:/login";
        }
        
        try {
            // Get transactions for the selected account
            Object transactions = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/accounts/" + accountNumber + "/transactions")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            
            model.addAttribute("currentUser", user);
            model.addAttribute("accountNumber", accountNumber);
            model.addAttribute("transactions", transactions);
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load transactions: " + e.getMessage());
        }
        
        return "transactions";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password, 
                           Model model, HttpSession session) {
        try {
            Map<String, Object> response = webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/customers/auth/login")
                .bodyValue(Map.of("email", email, "password", password))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
            
            session.setAttribute("user", response);
            session.setAttribute("token", response.get("token"));
            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerPost(@RequestParam String email, @RequestParam String firstName, 
                              @RequestParam String lastName, @RequestParam String password,
                              @RequestParam String phone, @RequestParam String address, Model model) {
        try {
            webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/customers")
                .bodyValue(java.util.Map.of(
                    "email", email,
                    "firstName", firstName,
                    "lastName", lastName,
                    "password", password,
                    "phone", phone,
                    "address", address
                ))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            model.addAttribute("success", "Registration successful! Please login.");
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/customers")
    public String customers(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", user);
        try {
            Object customers = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/customers")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> response.bodyToMono(String.class)
                        .map(body -> new RuntimeException("Service error: " + body)))
                .bodyToMono(Object.class)
                .block();
            System.out.println("Customers data: " + customers);
            model.addAttribute("customers", customers);
        } catch (Exception e) {
            System.out.println("Error fetching customers: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Failed to fetch customers: " + e.getMessage());
        }
        return "customers";
    }
    
    @GetMapping("/accounts")
    public String accounts(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", user);
        try {
            Object accounts = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/accounts")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            model.addAttribute("accounts", accounts);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch accounts: " + e.getMessage());
        }
        return "accounts";
    }
    
    @GetMapping("/transactions")
    public String transactions(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", user);
        try {
            Object transactions = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/transactions")
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            model.addAttribute("transactions", transactions);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to fetch transactions: " + e.getMessage());
        }
        return "transactions";
    }
    
    @PostMapping("/customers")
    public String createCustomer(@RequestParam String email, @RequestParam String firstName, 
                                @RequestParam String lastName, @RequestParam String password,
                                @RequestParam String phone, @RequestParam String address) {
        try {
            webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/customers")
                .bodyValue(java.util.Map.of(
                    "email", email,
                    "firstName", firstName,
                    "lastName", lastName,
                    "password", password,
                    "phone", phone,
                    "address", address
                ))
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        } catch (Exception e) {
            // Handle error
        }
        return "redirect:/customers";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}