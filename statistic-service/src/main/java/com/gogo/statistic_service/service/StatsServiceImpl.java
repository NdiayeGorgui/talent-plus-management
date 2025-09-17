package com.gogo.statistic_service.service;

import com.gogo.statistic_service.dto.CompetenceFrequencyDTO;
import com.gogo.statistic_service.dto.MonthlyCountDTO;
import com.gogo.statistic_service.dto.StatutCountDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {

   // private final WebClient candidatWebClient;
    private final WebClient competenceWebClient;

    private final WebClient recrutementWebClient;

    public StatsServiceImpl(
           // WebClient candidatWebClient,
            @Qualifier("competenceWebClient") WebClient competenceWebClient,
            @Qualifier("recrutementWebClient") WebClient recrutementWebClient) {
       // this.candidatWebClient = candidatWebClient;
        this.competenceWebClient = competenceWebClient;
        this.recrutementWebClient = recrutementWebClient;
    }

    // --- 1. Nombre de candidatures par statut ---
    @Override
    public List<StatutCountDTO> getCandidaturesParStatut() {
        List<Map<String, Object>> processus = recrutementWebClient.get()
                .uri("/candidatures-par-statut")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .collectList()
                .block();

        return processus.stream()
                .collect(Collectors.groupingBy(
                        p -> Optional.ofNullable((String) p.get("statut")).orElse("INCONNU"),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new StatutCountDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

    }

    // --- 2. Nombre de candidatures par mois ---
    @Override
    public List<MonthlyCountDTO> getCandidaturesParMois() {
        Map<String, Long> processusParMois = recrutementWebClient.get()
                .uri("/candidatures-par-mois")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Long>>() {})
                .block();

        return processusParMois.entrySet().stream()
                .map(e -> new MonthlyCountDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(MonthlyCountDTO::getMois))
                .toList();
    }

    // --- 3. Top 10 compétences ---
    @Override
    public List<CompetenceFrequencyDTO> getCompetencesFrequentes() {
        List<String> competences = competenceWebClient.get()
                .uri("/competences-frequentes") // → http://candidat-service/api/v1/competences/frequentes
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();

        return competences.stream()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet().stream()
                .map(e -> new CompetenceFrequencyDTO(e.getKey(), e.getValue()))
                .sorted((a, b) -> Long.compare(b.getFrequency(), a.getFrequency()))
                .limit(10)
                .collect(Collectors.toList());
    }

}
