package com.gogo.notification_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced   // ⚡ très important pour Eureka
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // Pour récupérer le candidat
    @Bean(name = "candidatWebClient")
    public WebClient candidatWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://candidat-service/api/v1/candidats")
                .build();
    }

}

