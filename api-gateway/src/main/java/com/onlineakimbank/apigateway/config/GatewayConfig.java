package com.onlineakimbank.apigateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("auth_service_docs", r -> r.path("/aggregate/auth-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/auth-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://auth-service"))

                .route("user_service_docs", r -> r.path("/aggregate/user-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/user-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://user-service"))

                .route("account_service_docs", r -> r.path("/aggregate/account-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregayr/account-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://account-service"))

                .route("card_service_docs", r -> r.path("/aggregate/card-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/card-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://card-service"))

                .route("transaction_service_docs", r -> r.path("/aggregate/transaction-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/transaction-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://transaction-service"))

                .route("payment_service_docs", r -> r.path("/aggregate/payment-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/payment-service/v3/docs", "/v3/api-docs"))
                        .uri("lb://payment-service"))

                .route("loan_service_docs", r -> r.path("/aggregate/loan-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/loan-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://loan-service"))

                .route("admin_service_docs", r -> r.path("/aggregate/admin-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/admin-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://admin-service"))

                .route("notification_service_docs", r -> r.path("/aggregate/notification-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/notification-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://notification-service"))

                .route("document_service_docs", r -> r.path("/aggregate/document-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggragate/document-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://document-service"))



                .route("auth_service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("authServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://auth-service"))

                .route("user_service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("userServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://user-service"))

                .route("admin_service", r -> r.path("/api/v1/admin/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("adminServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://admin-service"))

                .route("card_service", r -> r.path("/api/v1/card/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("cardServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://card-service"))

                .route("transaction_service", r -> r.path("/api/v1/transaction/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("transactionServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://transaction-service"))

                .route("payment_service", r -> r.path("/api/v1/payment/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("paymentServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://payment-service"))

                .route("loan_service", r -> r.path("/api/v1/loan/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("loanServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://loan-service"))

                .route("admin_service", r -> r.path("/api/v1/admin/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("adminServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://admin-service"))

                .route("notification_service", r -> r.path("/api/v1/notification/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("notificationServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://notification-service"))

                .route("document_service", r -> r.path("/api/v1/document/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("documentServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallbackRoute")))
                        .uri("lb://document-service"))


                .route("fallbackRoute", r -> r.path("/fallbackRoute")
                        .filters(f -> f.rewritePath("/fallbackRoute", "/"))
                        .uri("forward:/fallback"))


                .build();
    }
    
    @Bean
    public RouterFunction<ServerResponse> fallbackHandler() {
        return RouterFunctions.route()
                .GET("/fallback", request -> ServerResponse
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .bodyValue("[ 'Service Unavailable. Please try again later.' ]"))
                .build();
    }
}
