package ua.edu.ukma.cinemax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaXGateway {

    public static void main(String[] args) {
        SpringApplication.run(CinemaXGateway   .class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("film-service", r -> r.path("/api/film/**")
                        .uri("lb://film-service"))
                .route("media-service", r -> r.path("/media/**")
                        .uri("lb://media-service"))
                .route("app", r -> r.path("/**")
                        .uri("lb://app"))
                .build();
    }
}