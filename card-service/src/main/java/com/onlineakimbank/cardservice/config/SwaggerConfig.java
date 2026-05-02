package com.onlineakimbank.cardservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "card-service",
                version = "v1",
                description = "Service for registering and managing debit and loan cards for individuals users"
        )
)
public class SwaggerConfig {
}

