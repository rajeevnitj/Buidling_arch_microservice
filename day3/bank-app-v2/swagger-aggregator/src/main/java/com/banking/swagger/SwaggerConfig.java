package com.banking.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banking Application API")
                        .version("1.0.0")
                        .description("Combined API documentation for all banking services"))
                .addServersItem(new Server().url("http://localhost:8080").description("API Gateway"));
    }

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
                .group("customer-service")
                .pathsToMatch("/api/customers/**")
                .build();
    }

    @Bean
    public GroupedOpenApi accountApi() {
        return GroupedOpenApi.builder()
                .group("account-service")
                .pathsToMatch("/api/accounts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi transactionApi() {
        return GroupedOpenApi.builder()
                .group("transaction-service")
                .pathsToMatch("/api/transactions/**")
                .build();
    }
}