package com.example.gateway.config;
import com.example.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-SERVICE"))
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("category-service", r -> r.path("/api/v1/categories/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ORDER-SERVICE"))
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://USER-SERVICE"))
                .build();
    }
}
