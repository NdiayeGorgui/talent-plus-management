package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.LettreDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.LettreMotivationNotFoundException;
import com.gogo.candidat_service.model.LettreMotivation;
import com.gogo.candidat_service.repository.LettreMotivationRepository;
import com.gogo.candidat_service.service.LettreMotivationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lettres")
public class LettreMotivationController {

    private final LettreMotivationService lettreService;
    private final LettreMotivationRepository lettreMotivationRepository;

    public LettreMotivationController(LettreMotivationService lettreService, LettreMotivationRepository lettreMotivationRepository) {
        this.lettreService = lettreService;
        this.lettreMotivationRepository = lettreMotivationRepository;
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

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadLettre(@PathVariable("id") Long id) throws IOException {
        LettreMotivation lettre = lettreMotivationRepository.findById(id)
                .orElseThrow(() -> new IOException("Lettre non trouvée avec id " + id));

        File file = new File(lettre.getFichierUrl());
        if (!file.exists()) {
            throw new IOException("Fichier non trouvé sur le disque");
        }

        String originalFileName = file.getName(); // ex: 1696001234_lettre.docx
        String extension = "";

        int i = originalFileName.lastIndexOf('.');
        if (i >= 0) {
            extension = originalFileName.substring(i);
        }

        String downloadName = "lettre_" + id + extension;

        Resource resource = lettreService.downloadLettre(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
