package com.example.monolithbank.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
	    info = @Info(
	        title = "My Spring Boot Bank API",
	        version = "1.0",
	        description = "API documentation for my Spring based Banking application."
	    ),
	    servers = {
	        @Server(url = "http://localhost:8080", description = "Local Development Server")
	    }
	)
public class SwaggerConfig {

}
