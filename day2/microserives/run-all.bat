@echo off
echo Starting all microservices...

echo Starting Eureka Server...
cd eureka-server
start "Eureka" cmd /k "mvn spring-boot:run"
cd ..

timeout /t 20

echo Starting Config Server...
cd config-server
start "Config" cmd /k "mvn spring-boot:run"
cd ..

timeout /t 15

echo Starting Customer Service...
cd customer-service
start "Customer" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Account Service...
cd account-service
start "Account" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Transaction Service...
cd transaction-service
start "Transaction" cmd /k "mvn spring-boot:run"
cd ..

timeout /t 15

echo Starting API Gateway...
cd api-gateway
start "Gateway" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Web Client...
cd web-client
start "WebClient" cmd /k "mvn spring-boot:run"
cd ..

echo All services started!
echo Eureka: http://localhost:8761
echo Gateway: http://localhost:8080
echo Web UI: http://localhost:8090