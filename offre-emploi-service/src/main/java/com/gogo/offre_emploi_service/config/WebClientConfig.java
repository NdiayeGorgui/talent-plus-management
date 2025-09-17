package com.gogo.offre_emploi_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient recruteurWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://recruteur-service/api/v1/recruteurs")
                .build();
    }
}
