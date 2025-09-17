package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.mapper.CompetenceMapper;
import com.gogo.candidat_service.model.Competence;
import com.gogo.candidat_service.service.CompetenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/competences")
public class CompetenceController {
    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    // Ajouter une compétence à un candidat
    @PostMapping("/{candidatId}")
    public ResponseEntity<CompetenceDTO> addCompetence(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody CompetenceDTO dto) {
        return ResponseEntity.ok(competenceService.addCompetence(candidatId, dto));
    }

    // Récupérer toutes les compétences d’un candidat
    @GetMapping("/{candidatId}")
    public ResponseEntity<List<CompetenceDTO>> getCompetencesByCandidat(
            @PathVariable("candidatId") Long candidatId) {
        return ResponseEntity.ok(competenceService.getCompetencesByCandidat(candidatId));
    }

    // Supprimer une compétence par son id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable("id") Long id) {
        competenceService.deleteCompetence(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/competences-frequentes")
    public ResponseEntity<List<String>> getCompetencesFrequentes() {
        return ResponseEntity.ok(competenceService.findMostFrequentCompetences());
    }

}
