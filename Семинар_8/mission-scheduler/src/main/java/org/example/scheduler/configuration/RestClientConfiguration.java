package org.example.scheduler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    RestClient spaceOperationRestClient(SpaceCenterProperties properties) {
        if (properties.url() == null || properties.url().isBlank()) {
            throw new IllegalArgumentException(
                    "Не задан app.space-center-service.url"
            );
        }
        return RestClient.builder()
                .baseUrl(properties.url())
                .build();
    }
}
