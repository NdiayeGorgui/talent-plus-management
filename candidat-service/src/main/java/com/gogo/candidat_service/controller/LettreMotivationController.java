package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.LettreDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.LettreMotivationNotFoundException;
import com.gogo.candidat_service.service.LettreMotivationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lettres")
public class LettreMotivationController {

    private final LettreMotivationService lettreService;

    public LettreMotivationController(LettreMotivationService lettreService) {
        this.lettreService = lettreService;
    }

    //Lettre texte simple
    @PostMapping("/{candidatId}")
    public ResponseEntity<LettreDTO> createLettre(
            @PathVariable("candidatId") Long candidatId,
            @RequestBody LettreDTO lettreDTO) throws CandidatNotFoundException {
        return ResponseEntity.ok(lettreService.addLettre(candidatId, lettreDTO));
    }

    //lettre upload
    @PostMapping(value="/{candidatId}/upload",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LettreDTO> uploadLettre(
            @PathVariable("candidatId") Long candidatId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre) throws IOException, CandidatNotFoundException {
        return ResponseEntity.ok(lettreService.uploadLettre(candidatId, file, titre));
    }

    // Remplacer une lettre existante
    @PutMapping(value = "/{lettreId}/replace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LettreDTO> replaceLettre(
            @PathVariable("lettreId") Long lettreId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre) throws IOException, LettreMotivationNotFoundException {
        return ResponseEntity.ok(lettreService.replaceLettre(lettreId, file, titre));
    }

    // Supprimer une lettre
    @DeleteMapping("/{lettreId}")
    public ResponseEntity<Void> deleteLettre(@PathVariable("lettreId") Long lettreId) throws LettreMotivationNotFoundException {
        lettreService.deleteLettre(lettreId);
        return ResponseEntity.noContent().build();
    }

    // Récupérer toutes les lettres d’un candidat (DTO pour éviter cycle)
    @GetMapping("/{candidatId}/lettres")
    public ResponseEntity<List<LettreDTO>> getLettresByCandidat(@PathVariable("candidatId") Long candidatId) throws CandidatNotFoundException {
        List<LettreDTO> lettres = lettreService.getLettresByCandidat(candidatId);
        return ResponseEntity.ok(lettres);
    }
}
