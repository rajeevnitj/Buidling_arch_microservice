package com.banking.config;

import com.banking.entity.Account;
import com.banking.entity.Customer;
import com.banking.entity.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private final Random random = new Random();
    private final String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Lisa", "James", "Maria", "William", "Jennifer", "Richard", "Linda", "Charles", "Patricia", "Joseph", "Susan", "Thomas", "Jessica"};
    private final String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"};
    private final String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"};
    private final String[] streets = {"Main St", "Oak Ave", "Pine Rd", "Elm St", "Cedar Ave", "Maple Dr", "Park Ave", "First St", "Second St", "Third St"};

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        System.out.println("Initializing sample data...");

        // Create 100 customers
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@email.com";
            String phone = "+1" + String.format("%010d", random.nextLong() % 10000000000L);
            String address = (random.nextInt(9999) + 1) + " " + streets[random.nextInt(streets.length)] + ", " + cities[random.nextInt(cities.length)];

            Customer customer = new Customer(firstName, lastName, email, phone, address);
            customers.add(customer);
        }
        customerRepository.saveAll(customers);
        System.out.println("Created 100 customers");

        // Create 100 accounts (1-3 accounts per customer)
        List<Account> accounts = new ArrayList<>();
        Account.AccountType[] accountTypes = Account.AccountType.values();
        
        for (Customer customer : customers) {
            int numAccounts = random.nextInt(3) + 1; // 1-3 accounts per customer
            for (int j = 0; j < numAccounts && accounts.size() < 100; j++) {
                String accountNumber = "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
                Account.AccountType accountType = accountTypes[random.nextInt(accountTypes.length)];
                
                Account account = new Account(accountNumber, accountType, customer);
                account.setBalance(BigDecimal.valueOf(random.nextDouble() * 10000 + 100)); // Random balance between 100-10100
                accounts.add(account);
            }
        }
        accountRepository.saveAll(accounts);
        System.out.println("Created " + accounts.size() + " accounts");

        // Create 100 transactions
        List<Transaction> transactions = new ArrayList<>();
        Transaction.TransactionType[] transactionTypes = Transaction.TransactionType.values();
        String[] descriptions = {"ATM Withdrawal", "Online Purchase", "Salary Deposit", "Bill Payment", "Transfer", "Cash Deposit", "Check Deposit", "Refund", "Fee Payment", "Interest Credit"};

        for (int i = 0; i < 100; i++) {
            Account account = accounts.get(random.nextInt(accounts.size()));
            Transaction.TransactionType type = transactionTypes[random.nextInt(transactionTypes.length)];
            BigDecimal amount = BigDecimal.valueOf(random.nextDouble() * 1000 + 10); // Random amount between 10-1010
            String description = descriptions[random.nextInt(descriptions.length)];

            // Ensure withdrawal doesn't exceed balance
            if (type == Transaction.TransactionType.WITHDRAWAL && account.getBalance().compareTo(amount) < 0) {
                amount = account.getBalance().multiply(BigDecimal.valueOf(0.5)); // Withdraw 50% of balance
            }

            Transaction transaction = new Transaction(type, amount, description, account);
            transactions.add(transaction);

            // Update account balance
            if (type == Transaction.TransactionType.DEPOSIT) {
                account.setBalance(account.getBalance().add(amount));
            } else if (type == Transaction.TransactionType.WITHDRAWAL) {
                account.setBalance(account.getBalance().subtract(amount));
            }
        }
        transactionRepository.saveAll(transactions);
        accountRepository.saveAll(accounts); // Update balances
        System.out.println("Created 100 transactions");

        System.out.println("Sample data initialization completed!");
    }
}