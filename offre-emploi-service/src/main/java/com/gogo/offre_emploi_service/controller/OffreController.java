package com.gogo.offre_emploi_service.controller;

import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.service.OffreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offres")
public class OffreController {

    private final OffreService offreService;

    public OffreController(OffreService offreService) {
        this.offreService = offreService;
    }

    @PostMapping
    public ResponseEntity<OffreDTO> createOffre(@RequestBody OffreDTO dto) {
        return ResponseEntity.ok(offreService.createOffre(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OffreDTO> updateOffre(@PathVariable Long id, @RequestBody OffreDTO dto) {
        return ResponseEntity.ok(offreService.updateOffre(id, dto));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Void> closeOffre(@PathVariable Long id) {
        offreService.closeOffre(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OffreDTO>> getAllOffres() {
        return ResponseEntity.ok(offreService.getAllOffres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> getOffreById(@PathVariable Long id) {
        return ResponseEntity.ok(offreService.getOffreById(id));
    }

    @GetMapping("/recruteur/{recruteurId}")
    public ResponseEntity<List<OffreDTO>> getOffresByRecruteur(@PathVariable("recruteurId") Long recruteurId) {
        return ResponseEntity.ok(offreService.getOffresByRecruteur(recruteurId));
    }
}

