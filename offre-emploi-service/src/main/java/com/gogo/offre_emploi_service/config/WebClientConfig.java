package com.gogo.offre_emploi_service.config;

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

    @Bean
    public WebClient recruteurWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://recruteur-service/api/v1/recruteurs")
                .build();
    }

    @Bean
    public WebClient employeurWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://employeur-service/api/v1/employeurs")
                .build();
    }

}
