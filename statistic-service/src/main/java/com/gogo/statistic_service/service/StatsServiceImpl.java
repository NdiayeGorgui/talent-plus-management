package com.gogo.statistic_service.service;

import com.gogo.statistic_service.dto.CompetenceFrequencyDTO;
import com.gogo.statistic_service.dto.MonthlyCountDTO;
import com.gogo.statistic_service.dto.StatutCountDTO;
import com.gogo.statistic_service.exception.StatServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StatsServiceImpl implements StatsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatsServiceImpl.class);

    private final WebClient competenceWebClient;
    private final WebClient recrutementWebClient;

    public StatsServiceImpl(
            @Qualifier("competenceWebClient") WebClient competenceWebClient,
            @Qualifier("recrutementWebClient") WebClient recrutementWebClient) {
        this.competenceWebClient = competenceWebClient;
        this.recrutementWebClient = recrutementWebClient;
    }

    // --- 1. Nombre de candidatures par statut ---
    //@Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultCandidaturesParStatut")
    @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultCandidaturesParStatut")
    @Override
    public List<StatutCountDTO> getCandidaturesParStatut() {
        LOGGER.info("inside getCandidaturesParStatut");
        try {
            return recrutementWebClient.get()
                    .uri("/candidatures-par-statut")
                    .retrieve()
                    .bodyToFlux(StatutCountDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de la récupération des candidatures par statut : {}", e.getMessage());
            throw new StatServiceException("Impossible de récupérer les candidatures par statut", e);
        } catch (Exception e) {
            log.error("Erreur inconnue lors de getCandidaturesParStatut", e);
            throw new StatServiceException("Erreur interne du service de statistiques", e);
        }
    }

    // --- 2. Nombre de candidatures par mois ---
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultCandidaturesParMois")
    @Override
    public List<MonthlyCountDTO> getCandidaturesParMois() {
        try {
            Map<String, Long> processusParMois = recrutementWebClient.get()
                    .uri("/candidatures-par-mois")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Long>>() {})
                    .block();

            return processusParMois.entrySet().stream()
                    .map(e -> new MonthlyCountDTO(e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(MonthlyCountDTO::getMois))
                    .toList();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de la récupération des candidatures par mois : {}", e.getMessage());
            throw new StatServiceException("Impossible de récupérer les candidatures par mois", e);
        } catch (Exception e) {
            log.error("Erreur inconnue lors de getCandidaturesParMois", e);
            throw new StatServiceException("Erreur interne du service de statistiques", e);
        }
    }

    // --- 3. Compétences fréquentes ---
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultCompetencesFrequentes")

    @Override
    public List<CompetenceFrequencyDTO> getCompetencesFrequentes() {
        try {
            return competenceWebClient.get()
                    .uri("/competences-frequentes")
                    .retrieve()
                    .bodyToFlux(CompetenceFrequencyDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de la récupération des compétences fréquentes : {}", e.getMessage());
            throw new StatServiceException("Impossible de récupérer les compétences fréquentes", e);
        } catch (Exception e) {
            log.error("Erreur inconnue lors de getCompetencesFrequentes", e);
            throw new StatServiceException("Erreur interne du service de statistiques", e);
        }
    }

    public List<StatutCountDTO> getDefaultCandidaturesParStatut(Throwable throwable) {
        LOGGER.info("inside getDefaultCandidaturesParStatut");
        log.warn("Fallback activé pour getCandidaturesParStatut : {}", throwable.getMessage());
        return List.of(
                new StatutCountDTO("EN_ATTENTE", 12L),
                new StatutCountDTO("EN_COURS", 8L),
                new StatutCountDTO("REFUSE", 5L),
                new StatutCountDTO("ACCEPTE", 3L)
        );
    }

    public List<MonthlyCountDTO> getDefaultCandidaturesParMois(Throwable throwable) {
        log.warn("Fallback activé pour getCandidaturesParMois : {}", throwable.getMessage());
        return List.of(
                new MonthlyCountDTO("2025-07", 10L),
                new MonthlyCountDTO("2025-08", 15L),
                new MonthlyCountDTO("2025-09", 20L)
        );
    }
    public List<CompetenceFrequencyDTO> getDefaultCompetencesFrequentes(Throwable throwable) {
        log.warn("Fallback activé pour getCompetencesFrequentes : {}", throwable.getMessage());
        return List.of(
                new CompetenceFrequencyDTO("Java", 30L),
                new CompetenceFrequencyDTO("Spring Boot", 25L),
                new CompetenceFrequencyDTO("React", 18L),
                new CompetenceFrequencyDTO("SQL", 20L)
        );
    }


}
