@echo off
echo Starting Banking Application Monitoring Stack...

echo Starting ELK Stack and Prometheus...
docker-compose -f docker-compose-monitoring.yml up -d

echo Waiting for services to start...
timeout /t 30

echo Monitoring Stack Started!
echo.
echo Access URLs:
echo Kibana: http://localhost:5601
echo Prometheus: http://localhost:9090
echo Grafana: http://localhost:3000 (admin/admin)
echo Elasticsearch: http://localhost:9200
echo.
echo Combined Swagger Documentation: http://localhost:8084/swagger-ui.html
echo.
pause