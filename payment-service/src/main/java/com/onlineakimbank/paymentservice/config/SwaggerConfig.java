package com.onlineakimbank.paymentservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "payment-service",
                version = "v1",
                description = "Responsible for processing payments from individuals and companies, as well as for issuing cashback"
        )
)
public class SwaggerConfig {
}
