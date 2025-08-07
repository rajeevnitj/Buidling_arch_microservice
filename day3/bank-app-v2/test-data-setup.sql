-- Test data for banking application

-- Insert test customers
INSERT INTO banking_customers.customers (email, first_name, last_name, password, phone, address, role, created_at) VALUES
('john.doe@example.com', 'John', 'Doe', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '1234567890', '123 Main St', 'CUSTOMER', NOW()),
('jane.smith@example.com', 'Jane', 'Smith', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '0987654321', '456 Oak Ave', 'CUSTOMER', NOW()),
('admin@bank.com', 'Admin', 'User', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '5555555555', '789 Bank St', 'ADMIN', NOW());

-- Insert test accounts
INSERT INTO banking_accounts.accounts (account_number, account_type, balance, customer_id, created_at) VALUES
('ACC001', 'SAVINGS', 1500.00, 1, NOW()),
('ACC002', 'CHECKING', 2500.00, 1, NOW()),
('ACC003', 'SAVINGS', 3000.00, 2, NOW()),
('ACC004', 'CHECKING', 1000.00, 2, NOW());

-- Insert test transactions
INSERT INTO banking_transactions.transactions (account_number, transaction_type, amount, description, transaction_date) VALUES
('ACC001', 'DEPOSIT', 500.00, 'Initial deposit', NOW() - INTERVAL 5 DAY),
('ACC001', 'DEPOSIT', 1000.00, 'Salary deposit', NOW() - INTERVAL 3 DAY),
('ACC002', 'DEPOSIT', 2500.00, 'Initial deposit', NOW() - INTERVAL 4 DAY),
('ACC002', 'WITHDRAWAL', 100.00, 'ATM withdrawal', NOW() - INTERVAL 1 DAY),
('ACC003', 'DEPOSIT', 3000.00, 'Initial deposit', NOW() - INTERVAL 6 DAY),
('ACC004', 'DEPOSIT', 1000.00, 'Initial deposit', NOW() - INTERVAL 2 DAY);