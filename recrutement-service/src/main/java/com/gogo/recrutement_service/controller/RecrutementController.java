package com.gogo.recrutement_service.controller;

import com.gogo.recrutement_service.dto.PostulerDTO;
import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.dto.StatutCountDTO;
import com.gogo.recrutement_service.service.RecrutementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/recrutements")
public class RecrutementController {

    private final RecrutementService recrutementService;

    public RecrutementController(RecrutementService recrutementService) {
        this.recrutementService = recrutementService;
    }

    // 1. Candidat postule à une offre
    @PostMapping("/postuler")
    public ResponseEntity<ProcessusDTO> postuler(@RequestBody PostulerDTO dto) {
        return ResponseEntity.ok(recrutementService.createProcessus(dto.getCandidatId(), dto.getOffreId()));
    }


    // 2. Changer le statut
    @PutMapping("/{processusId}/statut")
    public ResponseEntity<ProcessusDTO> changerStatut(
            @PathVariable("processusId") Long processusId,
            @RequestParam("nouveauStatut") String nouveauStatut) {
        return ResponseEntity.ok(recrutementService.updateStatut(processusId, nouveauStatut));
    }

    // 3. Lister
    @GetMapping
    public ResponseEntity<List<ProcessusDTO>> getAll() {
        return ResponseEntity.ok(recrutementService.getAllProcessus());
    }

    // 4. Liste des candidatures d'un candidat
    @GetMapping("/candidat/{candidatId}")
    public ResponseEntity<List<ProcessusDTO>> getByCandidat(@PathVariable("candidatId") Long candidatId) {
        return ResponseEntity.ok(recrutementService.getByCandidat(candidatId));
    }

    // 5. Processus d'une offre
    @GetMapping("/offre/{offreId}")
    public ResponseEntity<List<ProcessusDTO>> getByOffre(@PathVariable("offreId") Long offreId) {
        return ResponseEntity.ok(recrutementService.getByOffre(offreId));
    }

    // 6. Historique
    @GetMapping("/{processusId}/historique")
    public ResponseEntity<List<HistoriqueDTO>> getHistorique(@PathVariable("processusId") Long processusId) {
        return ResponseEntity.ok(recrutementService.getHistorique(processusId));
    }
    // 7. Stat candidatures par mois
    @GetMapping("/candidatures-par-mois")
    public Map<String, Long> getCandidaturesParMois() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        return recrutementService.getAllProcessus().stream()
                .collect(Collectors.groupingBy(p -> p.getDateMaj().format(fmt), Collectors.counting()));
    }

    // 3. Stat candidatures par statut
    @GetMapping("/candidatures-par-statut")
    public List<StatutCountDTO> getCandidaturesParStatut() {
        return recrutementService.getAllProcessus().stream()
                .collect(Collectors.groupingBy(
                        p -> Optional.ofNullable(p.getStatut()).orElse("INCONNU"),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(e -> new StatutCountDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }



}
