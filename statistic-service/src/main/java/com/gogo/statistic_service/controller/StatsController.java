package com.gogo.statistic_service.controller;

import com.gogo.statistic_service.dto.CompetenceFrequencyDTO;
import com.gogo.statistic_service.dto.MonthlyCountDTO;
import com.gogo.statistic_service.dto.StatutCountDTO;
import com.gogo.statistic_service.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/recrutements/candidatures-par-statut")
    public ResponseEntity<List<StatutCountDTO>> getCandidaturesParStatut() {
        return ResponseEntity.ok(statsService.getCandidaturesParStatut());
    }

    @GetMapping("/recrutements/candidatures-par-mois")
    public ResponseEntity<List<MonthlyCountDTO>> getCandidaturesParMois() {
        return ResponseEntity.ok(statsService.getCandidaturesParMois());
    }

    @GetMapping("/competences/competences-frequentes")
    public ResponseEntity<List<CompetenceFrequencyDTO>> getCompetencesFrequentes() {
        return ResponseEntity.ok(statsService.getCompetencesFrequentes());
    }
}

