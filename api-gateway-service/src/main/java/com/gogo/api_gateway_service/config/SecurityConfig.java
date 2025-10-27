package com.gogo.api_gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // ✅ Indispensable pour activer la sécurité WebFlux (Spring Cloud Gateway)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // ✅ Autoriser Prometheus et Health Check sans authentification
                        .pathMatchers(
                                "/actuator/**"
                        ).permitAll()

                        // ✅ Autoriser les routes d'authentification et docs swagger du service utilisateur
                        .pathMatchers(
                                "/utilisateur-service/api/v1/auth/**",
                                "/utilisateur-service/v3/api-docs/**",
                                "/utilisateur-service/swagger-ui/**"
                        ).permitAll()

                        // 🔐 Tout le reste nécessite un JWT valide
                        .anyExchange().authenticated()
                )
                // ✅ Appliquer le filtre JWT seulement après les exclusions ci-dessus
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
