package com.example.gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRouteLocator {

    private static final String DEAL_URI = "http://localhost:8080";
    private static final String APPLICATION_URI = "http://localhost:8006";

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("deal/**",
                        route -> route.path("/deal/**")
                                .uri(DEAL_URI +"/deal/**"))
                .route("application/**",
                        route -> route.path("/application/**")
                                .uri(APPLICATION_URI +"/application/**"))
                .build();
    }
}
