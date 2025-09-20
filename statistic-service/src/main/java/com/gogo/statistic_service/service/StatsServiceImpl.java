package com.gogo.statistic_service.service;

import com.gogo.statistic_service.dto.CompetenceFrequencyDTO;
import com.gogo.statistic_service.dto.MonthlyCountDTO;
import com.gogo.statistic_service.dto.StatutCountDTO;
import com.gogo.statistic_service.exception.StatServiceException;
import lombok.extern.slf4j.Slf4j;
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

    private final WebClient competenceWebClient;
    private final WebClient recrutementWebClient;

    public StatsServiceImpl(
            @Qualifier("competenceWebClient") WebClient competenceWebClient,
            @Qualifier("recrutementWebClient") WebClient recrutementWebClient) {
        this.competenceWebClient = competenceWebClient;
        this.recrutementWebClient = recrutementWebClient;
    }

    // --- 1. Nombre de candidatures par statut ---
    @Override
    public List<StatutCountDTO> getCandidaturesParStatut() {
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
}
