package com.gogo.statistic_service.config;

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

    // Pour récupérer les candidats (date de candidature, infos perso)
    @Bean(name = "candidatWebClient")
    public WebClient candidatWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://candidat-service/api/v1/candidats")
                .build();
    }

    // Pour récupérer les compétences
    @Bean(name = "competenceWebClient")
    public WebClient competenceWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://candidat-service/api/v1/competences")
                .build();
    }

    // Pour récupérer les processus de candidature
    @Bean(name = "recrutementWebClient")
    public WebClient recrutementWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://recrutement-service/api/v1/recrutements")
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

    @Bean(name = "offreWebClient")
    public WebClient offreWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://offre-service/api/v1/offres")
                .build();
    }

}

