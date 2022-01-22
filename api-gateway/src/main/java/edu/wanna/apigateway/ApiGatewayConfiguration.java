package edu.wanna.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/auth/**").filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route(p -> p.path("/auth/**").filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route(p -> p.path("/client/**").filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route(p -> p.path("/user/**").filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route(p -> p.path("/dietitian/**").filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route(p -> p.path("/appointments/**").filters(f -> f.filter(filter))
                        .uri("lb://appointments-service"))
                .build();
    }
}
