package com.onlineakimbank.accountservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "account-service",
                version = "v1",
                description = "Responsible for account registration, login, currency conversion and account management"
        )
)
public class SwaggerConfig {
}
