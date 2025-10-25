package com.gogo.offre_emploi_service.controller;

import com.gogo.offre_emploi_service.dto.OffreCountDTO;
import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.exception.OffreNotFoundException;
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
    public ResponseEntity<OffreDTO> updateOffre(@PathVariable("id") Long id, @RequestBody OffreDTO dto) throws OffreNotFoundException {
        return ResponseEntity.ok(offreService.updateOffre(id, dto));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Void> closeOffre(@PathVariable("id") Long id) throws OffreNotFoundException {
        offreService.closeOffre(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/open")
    public ResponseEntity<Void> openOffre(@PathVariable("id") Long id) throws OffreNotFoundException {
        offreService.openOffre(id); // Implémente cette méthode dans le service si pas encore fait
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<OffreDTO>> getAllOffres() {
        return ResponseEntity.ok(offreService.getAllOffres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> getOffreById(@PathVariable("id") Long id) throws OffreNotFoundException {
        return ResponseEntity.ok(offreService.getOffreById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable("id") Long id) throws OffreNotFoundException {
        offreService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/recruteurs/{recruteurId}")
    public ResponseEntity<List<OffreDTO>> getOffresByRecruteur(@PathVariable("recruteurId") Long recruteurId) {
        return ResponseEntity.ok(offreService.getOffresByRecruteur(recruteurId));
    }

    @GetMapping("/employeurs/{employeurId}")
    public ResponseEntity<List<OffreDTO>> getOffresByEmployeur(@PathVariable("employeurId") Long employeurId) {
        return ResponseEntity.ok(offreService.getOffresByEmployeur(employeurId));
    }

    @GetMapping("/recruteur/{id}/ids")
    public List<Long> getOffreIdsByRecruteur(@PathVariable("id") Long id) {
        return offreService.findOffreIdsByRecruteurId(id);
    }

    @GetMapping("/employeur/{id}/ids")
    public List<Long> getOffreIdsByEmployeur(@PathVariable("id") Long id) {
        return offreService.findOffreIdsByEmployeurId(id);
    }

    @GetMapping("/count-by-recruteur")
    public ResponseEntity<List<OffreCountDTO>> getCountByRecruteur() {
        return ResponseEntity.ok(offreService.countOffresByRecruteur());
    }

    @GetMapping("/count-by-employeur")
    public ResponseEntity<List<OffreCountDTO>> getCountByEmployeur() {
        return ResponseEntity.ok(offreService.countOffresByEmployeur());
    }



}

