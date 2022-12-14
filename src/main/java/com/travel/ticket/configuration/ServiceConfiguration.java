package com.travel.ticket.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Common configuration to define common RestTemplate behavior for all project
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }

}
