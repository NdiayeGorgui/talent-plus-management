package com.gogo.statistic_service.controller;

import com.gogo.statistic_service.dto.CompetenceFrequencyDTO;
import com.gogo.statistic_service.dto.MonthlyCountDTO;
import com.gogo.statistic_service.dto.OffreCountDTO;
import com.gogo.statistic_service.dto.StatutCountDTO;
import com.gogo.statistic_service.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
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

    /**
     * 🔹 Récupère le nombre d'offres par employeur
     */
    @GetMapping("/offres/employeurs")
    public ResponseEntity<List<OffreCountDTO>> getOffresParEmployeur() {
        log.info("📊 Récupération du nombre d’offres par employeur...");
        List<OffreCountDTO> result = statsService.getNombreOffresParEmployeur();
        return ResponseEntity.ok(result);
    }

    /**
     * 🔹 Récupère le nombre d'offres par recruteur
     */
    @GetMapping("/offres/recruteurs")
    public ResponseEntity<List<OffreCountDTO>> getOffresParRecruteur() {
        log.info("📊 Récupération du nombre d’offres par recruteur...");
        List<OffreCountDTO> result = statsService.getNombreOffresParRecruteur();
        return ResponseEntity.ok(result);
    }
}

