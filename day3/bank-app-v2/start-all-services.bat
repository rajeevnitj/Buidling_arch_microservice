@echo off
echo Starting Banking Application Services...

echo Starting MySQL Database...
docker-compose up -d

echo Waiting for MySQL to be ready...
timeout /t 10

echo Starting Eureka Server...
start "Eureka Server" cmd /k "cd eureka-server && mvn spring-boot:run"
timeout /t 15

echo Starting Config Server...
start "Config Server" cmd /k "cd config-server && mvn spring-boot:run"
timeout /t 15


echo Starting Customer Service...
start "Customer Service" cmd /k "cd customer-service && mvn spring-boot:run"
timeout /t 10

echo Starting Account Service...
start "Account Service" cmd /k "cd account-service && mvn spring-boot:run"
timeout /t 10

echo Starting Transaction Service...
start "Transaction Service" cmd /k "cd transaction-service && mvn spring-boot:run"
timeout /t 10

echo Starting API Gateway...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 10

echo Starting Web Client...
start "Web Client" cmd /k "cd web-client && mvn spring-boot:run"
timeout /t 5

echo Starting Swagger Aggregator...
start "Swagger Aggregator" cmd /k "cd swagger-aggregator && mvn spring-boot:run"

echo.
echo All services are starting...
echo.
echo Access URLs:
echo Web Application: http://localhost:8090
echo API Gateway: http://localhost:8080
echo Eureka Dashboard: http://localhost:8761
echo Combined Swagger: http://localhost:8084/swagger-ui.html
echo.
echo Individual Service Swagger:
echo Customer Service: http://localhost:8081/swagger-ui.html
echo Account Service: http://localhost:8082/swagger-ui.html
echo Transaction Service: http://localhost:8083/swagger-ui.html
echo.
pause