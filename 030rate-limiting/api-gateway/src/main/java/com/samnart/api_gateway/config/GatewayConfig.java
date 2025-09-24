package com.samnart.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r
                .path("/api/users/**")
                .uri("lb://user-service")
                .filters(f -> f
                    .stripPrefix(0)
                    .circuitBreaker(config -> config
                        .setName("user-service-circuit-breaker")
                        .setFallbackUri("/fallback/user-service"))
                    .retry(config -> config
                        .setRetries(3)
                        .setStatuses("5XX")
                        .setBackoff(java.time.Duration.ofMillis(100), java.time.Duration.ofMillis(1000), 2, false))))

            .route("product-service", r -> r
                .path("/api/products/**")
                .uri("lb://product-service")
                .filters(f -> f
                    .stripPrefix(0)
                    .circuitBreaker(config -> config
                        .setName("product-service-circuit")
                        .setFallbackUri("/fallback/product-service"))
                    .retry(config -> config
                        .setRetries(3)
                        .setStatuses("5XX"))))

            .route("order-service", r -> r
                .path("/api/orders/**")
                .uri("lb://order-service")
                .filters(f -> f
                    .stripPrefix(0)
                    .circuitBreaker(config -> config
                        .setName("order-service-circuit-breaker")
                        .setFallbackUri("/fallback/order-service"))))
                    
            .route("notification-service", r -> r
                .path("/api/notifications/**")
                .uri("lb://notification-service")
                .filters(f -> f
                    .stripPrefix(0)
                    .circuitBreaker(config -> config
                        .setName("notification-service-circuit-breaker")
                        .setFallbackUri("/fallback/notification-service"))))

            .route("config-service", r -> r 
                .path("/internal/config/**")
                .uri("lb://rate-limit-config-service")
                .filters(f -> f.stripPrefix(1)))

            .build();
            
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/*", corsConfig);

        return new CorsWebFilter(source);
    }
}