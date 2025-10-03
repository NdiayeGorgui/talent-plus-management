package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.MetadonneeRHDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.MetaDonneeRHNotFoundException;
import com.gogo.candidat_service.service.MetaDonneeRHService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metadonnees")
public class MetaDonneeRHController {

    private final MetaDonneeRHService service;

    public MetaDonneeRHController(MetaDonneeRHService service) {
        this.service = service;
    }

    /**
     * POST /api/v1/candidats/{candidatId}/metadonnees
     * Créer des métadonnées RH pour un candidat
     */
    @PostMapping("/{candidatId}")
    public ResponseEntity<MetadonneeRHDTO> addMetadonneeRH(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody MetadonneeRHDTO dto
    ) throws CandidatNotFoundException {
        MetadonneeRHDTO created = service.addMetadonneeRh(candidatId, dto);
        return ResponseEntity.ok(created);
    }

    /**
     * PUT /api/v1/candidats/{candidatId}/metadonnees
     * Modifier les métadonnées RH du candidat
     */
    @PutMapping("/{candidatId}")
    public ResponseEntity<MetadonneeRHDTO> updateMetadonneeRH(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody MetadonneeRHDTO dto
    ) throws CandidatNotFoundException, MetaDonneeRHNotFoundException {
        MetadonneeRHDTO updated = service.updateMetadonneeRh(candidatId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * GET /api/v1/candidats/{candidatId}/metadonnees
     * Récupérer les métadonnées RH du candidat
     */
    @GetMapping("/{candidatId}")
    public ResponseEntity<MetadonneeRHDTO> getMetadonneeRH(
            @PathVariable("candidatId") Long candidatId
    ) throws CandidatNotFoundException, MetaDonneeRHNotFoundException {
        MetadonneeRHDTO dto = service.getMetadonneeRhByCandidatId(candidatId);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/v1/candidats/{candidatId}/metadonnees/{metadonneeId}
     * Supprimer des métadonnées RH spécifiques
     */
    @DeleteMapping("/{metadonneeId}")
    public ResponseEntity<Void> delete(
            @PathVariable("metadonneeId") Long id
    ) throws MetaDonneeRHNotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
