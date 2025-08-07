# Banking Application - Microservices Architecture

A comprehensive banking application built with Java 21, Spring Boot 3.5.4, and microservices architecture.

## Architecture Components

### Core Services
- **Config Server** (Port 8888) - Centralized configuration management
- **Eureka Server** (Port 8761) - Service discovery and registration
- **API Gateway** (Port 8080) - Routing and centralized security
- **Customer Service** (Port 8081) - Customer management
- **Account Service** (Port 8082) - Account management with batch processing
- **Transaction Service** (Port 8083) - Transaction processing
- **Web Client** (Port 8090) - Spring MVC web interface

### Features
- **Service Discovery**: Eureka for automatic service registration and discovery
- **Load Balancing**: Spring Cloud LoadBalancer for client-side load balancing
- **Centralized Configuration**: Spring Cloud Config Server
- **API Documentation**: Swagger/OpenAPI 3 integration
- **Security**: Role-based access control with Spring Security
- **Caching**: Spring Cache abstraction
- **Batch Processing**: Spring Batch for quarterly interest calculation
- **Database**: MySQL with separate databases for each service
- **Sync Communication**: Feign clients for inter-service communication

## Prerequisites
- Java 21
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.0

## Quick Start

1. **Start MySQL Database**:
   ```bash
   docker-compose up -d
   ```

2. **Start All Services**:
   ```bash
   start-services.bat
   ```

3. **Access Applications**:
   - Web Client: http://localhost:8090
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761
   - Swagger UI: 
     - Customer Service: http://localhost:8081/swagger-ui.html
     - Account Service: http://localhost:8082/swagger-ui.html
     - Transaction Service: http://localhost:8083/swagger-ui.html

## API Endpoints

### Customer Service
- `POST /api/customers` - Create customer
- `GET /api/customers/{id}` - Get customer by ID
- `GET /api/customers` - Get all customers (Admin only)
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer (Admin only)

### Account Service
- `POST /api/accounts` - Create account
- `GET /api/accounts/{id}` - Get account by ID
- `GET /api/accounts/customer/{customerId}` - Get accounts by customer
- `PUT /api/accounts/{accountNumber}/balance` - Update balance
- `POST /api/accounts/interest/apply` - Apply quarterly interest (Admin only)

### Transaction Service
- `POST /api/transactions/deposit` - Deposit money
- `POST /api/transactions/withdraw` - Withdraw money
- `POST /api/transactions/transfer` - Transfer money
- `GET /api/transactions/account/{accountNumber}` - Get transactions by account

## Batch Processing

The application includes a Spring Batch job that automatically calculates and applies quarterly interest (4 times per year) to savings accounts. The interest rate is set to 3.5% annually for savings accounts.

To manually trigger interest calculation:
```bash
POST /api/accounts/interest/apply
```

## Security

The application implements role-based security with two roles:
- **CUSTOMER**: Can access their own data
- **ADMIN**: Can access all data and perform administrative operations

## Database Schema

The application uses three separate MySQL databases:
- `banking_customers` - Customer data
- `banking_accounts` - Account data
- `banking_transactions` - Transaction data

## Configuration

All services are configured through the Config Server. Common configurations are stored in `config-repo/application.yml`.

## Monitoring

All services expose actuator endpoints for monitoring:
- Health: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`

## Load Balancing

The application uses Spring Cloud LoadBalancer for client-side load balancing. Multiple instances of services can be started on different ports for horizontal scaling.

## Caching

Services implement caching using Spring Cache abstraction to improve performance for frequently accessed data.