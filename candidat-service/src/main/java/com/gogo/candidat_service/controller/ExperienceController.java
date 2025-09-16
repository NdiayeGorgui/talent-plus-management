package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.ExperienceDTO;
import com.gogo.candidat_service.model.Experience;
import com.gogo.candidat_service.service.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    //save experience
    @PostMapping("/{candidatId}")
    public ResponseEntity<ExperienceDTO> addExperience(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody ExperienceDTO dto) {
        return ResponseEntity.ok(experienceService.addExperience(candidatId, dto));
    }

    // Récupérer toutes les expériences d’un candidat
    @GetMapping("/{candidatId}/experiences")
    public ResponseEntity<List<ExperienceDTO>> getExperiencesByCandidat(@PathVariable("candidatId") Long candidatId) {
        List<ExperienceDTO> experiences = experienceService.getExperiencesByCandidat(candidatId);
        return ResponseEntity.ok(experiences);
    }


    // Supprimer une expérience par son id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable("id") Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

}
