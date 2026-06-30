package com.barani.travel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI travelBookingAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Travel Booking Microservices API")
                        .description("Backend APIs for Travel Booking System")
                        .version("1.0"));
    }
}