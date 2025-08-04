package com.banking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bankingOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development server");

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("Banking Application API")
                        .description("REST API for Banking Application with Customer, Account, and Transaction management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Banking Team")
                                .email("support@banking.com")));
    }
}