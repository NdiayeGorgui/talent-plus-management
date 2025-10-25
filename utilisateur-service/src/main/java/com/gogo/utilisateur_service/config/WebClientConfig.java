package com.gogo.utilisateur_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean(name = "candidatWebClient")
    public WebClient candidatWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://candidat-service/api/v1/candidats")
                .build();
    }

    @Bean(name = "recruteurWebClient")
    public WebClient recruteurWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://recruteur-service/api/v1/recruteurs")
                .build();
    }

    @Bean(name = "employeurWebClient")
    public WebClient employeurWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://employeur-service/api/v1/employeurs")
                .build();
    }

    @Bean(name = "adminWebClient")
    public WebClient adminWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://recruteur-service/api/v1/recruteurs")
                .build();
    }

    @Bean(name = "offreWebClient")
    public WebClient offreWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://offre-service/api/v1/offres")
                .build();
    }
}

