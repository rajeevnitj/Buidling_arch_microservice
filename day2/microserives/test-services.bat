@echo off
echo Testing microservices startup...

echo Starting Eureka Server...
cd eureka-server
start "Eureka" cmd /k "mvn spring-boot:run"
cd ..

timeout /t 30

echo Starting Config Server...
cd config-server  
start "Config" cmd /k "mvn spring-boot:run"
cd ..

timeout /t 20

echo Starting Customer Service...
cd customer-service
start "Customer" cmd /k "mvn spring-boot:run"
cd ..

echo Services started. Check individual windows for status.