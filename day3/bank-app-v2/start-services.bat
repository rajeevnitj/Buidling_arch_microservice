@echo off
echo Starting Banking Application Services...

echo Starting MySQL...
docker-compose up -d

echo Waiting for MySQL to be ready...
timeout /t 30

echo Starting Eureka Server...
cd eureka-server
start "Eureka-Server" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Config Server...
cd config-server
start "Config-Server" cmd /k "mvn spring-boot:run"
cd ..

echo Waiting for Config Server...
timeout /t 30


echo Waiting for Eureka Server...
timeout /t 30

echo Starting Customer Service...
cd customer-service
start "Customer-Service" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Account Service...
cd account-service
start "Account-Service" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Transaction Service...
cd transaction-service
start "Transaction-Service" cmd /k "mvn spring-boot:run"
cd ..

echo Waiting for core services...
timeout /t 20

echo Starting API Gateway...
cd api-gateway
start "API-Gateway" cmd /k "mvn spring-boot:run"
cd ..

echo Starting Web Client...
cd web-client
start "Web-Client" cmd /k "mvn spring-boot:run"
cd ..

echo All services are starting...
echo Config Server: http://localhost:8888
echo Eureka Server: http://localhost:8761
echo API Gateway: http://localhost:8080
echo Web Client: http://localhost:8090
echo Customer Service: http://localhost:8081
echo Account Service: http://localhost:8082
echo Transaction Service: http://localhost:8083