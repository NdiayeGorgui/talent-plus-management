package com.gogo.recrutement_service.controller;

import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.service.RecrutementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/recrutements")
public class RecrutementController {

    private final RecrutementService recrutementService;

    public RecrutementController(RecrutementService recrutementService) {
        this.recrutementService = recrutementService;
    }

    // 1. Candidat postule à une offre
    @PostMapping
    public ResponseEntity<ProcessusDTO> postuler(
            @RequestParam Long candidatId,
            @RequestParam Long offreId) {
        return ResponseEntity.ok(recrutementService.createProcessus(candidatId, offreId));
    }

    // 2. Changer le statut
    @PutMapping("/{processusId}/statut")
    public ResponseEntity<ProcessusDTO> changerStatut(
            @PathVariable Long processusId,
            @RequestParam String nouveauStatut) {
        return ResponseEntity.ok(recrutementService.updateStatut(processusId, nouveauStatut));
    }

    // 3. Lister
    @GetMapping
    public ResponseEntity<List<ProcessusDTO>> getAll() {
        return ResponseEntity.ok(recrutementService.getAllProcessus());
    }

    @GetMapping("/candidat/{candidatId}")
    public ResponseEntity<List<ProcessusDTO>> getByCandidat(@PathVariable Long candidatId) {
        return ResponseEntity.ok(recrutementService.getByCandidat(candidatId));
    }

    @GetMapping("/offre/{offreId}")
    public ResponseEntity<List<ProcessusDTO>> getByOffre(@PathVariable Long offreId) {
        return ResponseEntity.ok(recrutementService.getByOffre(offreId));
    }

    // 4. Historique
    @GetMapping("/{processusId}/historique")
    public ResponseEntity<List<HistoriqueDTO>> getHistorique(@PathVariable Long processusId) {
        return ResponseEntity.ok(recrutementService.getHistorique(processusId));
    }

    @GetMapping("/candidatures-par-mois")
    public Map<String, Long> getCandidaturesParMois() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        return recrutementService.getAllProcessus().stream()
                .collect(Collectors.groupingBy(p -> p.getDateMaj().format(fmt), Collectors.counting()));
    }

    @GetMapping("/candidatures-par-statut")
    public Map<String, Long> getCandidaturesParStatut() {
        return recrutementService.getAllProcessus().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getStatut(),
                        Collectors.counting()
                ));
    }


}
