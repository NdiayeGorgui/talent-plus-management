package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.dto.CompetenceFrequencyDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CompetenceNotFoundException;
import com.gogo.candidat_service.service.CompetenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/competences")
public class CompetenceController {
    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    // Ajouter une compétence à un candidat
    @PostMapping("/{candidatId}")
    public ResponseEntity<List<CompetenceDTO>> addCompetences(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody  List<CompetenceDTO> dtos) throws CandidatNotFoundException {
        return ResponseEntity.ok(competenceService.addCompetences(candidatId, dtos));
    }

    // Récupérer toutes les compétences d’un candidat
    @GetMapping("/{candidatId}")
    public ResponseEntity<List<CompetenceDTO>> getCompetencesByCandidat(
            @PathVariable("candidatId") Long candidatId) throws CandidatNotFoundException {
        return ResponseEntity.ok(competenceService.getCompetencesByCandidat(candidatId));
    }

    // Supprimer une compétence par son id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable("id") Long id) throws CompetenceNotFoundException {
        competenceService.deleteCompetence(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/competences-frequentes")
    public ResponseEntity<List<CompetenceFrequencyDTO>> getCompetencesFrequentes() {
        return ResponseEntity.ok(competenceService.findMostFrequentCompetences());
    }



}
