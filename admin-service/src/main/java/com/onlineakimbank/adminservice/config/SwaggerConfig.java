package com.onlineakimbank.adminservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "admin-service",
                version = "v1",
                description = "Admin panel for managing the bank and processing loan applications"
        )
)
public class SwaggerConfig {
}
