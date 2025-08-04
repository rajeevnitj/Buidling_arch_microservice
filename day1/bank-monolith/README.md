# Banking Application

A Spring Boot monolith banking application with customer, account, and transaction management.

## Features

- **Customer Management**: Create, read, update, delete customers
- **Account Management**: Create and manage bank accounts (Savings, Checking, Business)
- **Transaction Management**: Handle deposits and withdrawals
- **Pagination & Sorting**: All endpoints support pagination and sorting
- **Swagger Documentation**: Interactive API documentation
- **Sample Data**: 100 sample records for each entity

## Technology Stack

- Java 21
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL 8.0
- Swagger/OpenAPI 3
- Maven

## Prerequisites

- Java 21
- MySQL 8.0
- Maven 3.6+

## Setup Instructions

1. **Clone and navigate to the project**
   ```bash
   cd banking-app
   ```

2. **Setup MySQL Database**
   - Create a MySQL database named `banking_db`
   - Update database credentials in `src/main/resources/application.yml`

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api-docs

## API Endpoints

### Customer Management
- `GET /api/customers` - Get all customers (with pagination, sorting, search)
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Account Management
- `GET /api/accounts` - Get all accounts (with pagination, sorting)
- `GET /api/accounts/{id}` - Get account by ID
- `GET /api/accounts/customer/{customerId}` - Get accounts by customer
- `POST /api/accounts` - Create new account
- `DELETE /api/accounts/{id}` - Delete account

### Transaction Management
- `GET /api/transactions` - Get all transactions (with pagination, sorting)
- `GET /api/transactions/{id}` - Get transaction by ID
- `GET /api/transactions/account/{accountId}` - Get transactions by account
- `GET /api/transactions/customer/{customerId}` - Get transactions by customer
- `POST /api/transactions` - Create new transaction

## Sample Data

The application automatically creates 100 sample records for each entity on startup:
- 100 Customers with realistic names, emails, and addresses
- 100 Accounts distributed across customers with random balances
- 100 Transactions with various types and amounts

## Pagination & Sorting Examples

- Get customers with pagination: `GET /api/customers?page=0&size=10`
- Sort customers by name: `GET /api/customers?sort=firstName,asc`
- Search customers: `GET /api/customers?search=john`
- Complex query: `GET /api/customers?page=0&size=5&sort=createdAt,desc&search=smith`

## Database Schema

The application uses three main entities:
- **Customer**: Personal information and contact details
- **Account**: Bank accounts linked to customers
- **Transaction**: Financial transactions linked to accounts

All entities include audit fields (createdAt) and proper relationships with foreign keys.