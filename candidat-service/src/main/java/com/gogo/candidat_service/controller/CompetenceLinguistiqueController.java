package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.CompetenceLinguistiqueDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CompetenceLinguistiqueNotFoundException;
import com.gogo.candidat_service.service.CompetenceLinguistiqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/competences-linguistiques")
public class CompetenceLinguistiqueController {

    private final CompetenceLinguistiqueService competenceLinguistiqueService;

    public CompetenceLinguistiqueController(CompetenceLinguistiqueService competenceLinguistiqueService) {
        this.competenceLinguistiqueService = competenceLinguistiqueService;
    }

    // ✅ Ajouter des compétences linguistiques à un candidat
    @PostMapping("/{candidatId}")
    public ResponseEntity<List<CompetenceLinguistiqueDTO>> addCompetencesLinguistiques(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody List<CompetenceLinguistiqueDTO> dtos
    ) throws CandidatNotFoundException {
        return ResponseEntity.ok(
                competenceLinguistiqueService.addCompetencesLinguistiques(candidatId, dtos)
        );
    }

    // ✅ Récupérer toutes les compétences linguistiques d’un candidat
    @GetMapping("/{candidatId}")
    public ResponseEntity<List<CompetenceLinguistiqueDTO>> getCompetencesLinguistiquesByCandidat(
            @PathVariable("candidatId") Long candidatId
    ) throws CandidatNotFoundException {
        return ResponseEntity.ok(
                competenceLinguistiqueService.getCompetencesLinguistiquesByCandidat(candidatId)
        );
    }

    // ✅ Supprimer une compétence linguistique par son id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetenceLinguistique(
            @PathVariable("id") Long id
    ) throws CompetenceLinguistiqueNotFoundException {
        competenceLinguistiqueService.deleteCompetenceLinguistique(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{candidatId}")
    public ResponseEntity<Void> updateCompetencesLinguistiques(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody List<CompetenceLinguistiqueDTO> dtos) throws CandidatNotFoundException, CompetenceLinguistiqueNotFoundException {

        competenceLinguistiqueService.updateCompetencesLinguistiques(candidatId, dtos);
        return ResponseEntity.ok().build();
    }

}
