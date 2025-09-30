package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.CvDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CvNotFoundException;
import com.gogo.candidat_service.service.CvService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cvs")
public class CvController {

    private final CvService cvService;

    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    @PostMapping(value="/{candidatId}",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CvDTO> uploadCv(
            @PathVariable("candidatId") Long candidatId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("titre") String titre) throws IOException, CandidatNotFoundException {
        return ResponseEntity.ok(cvService.uploadCv(candidatId, file, titre));
    }
    // 🔹 Remplacer un CV existant
    @PostMapping(value = "/{cvId}/replace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CvDTO> replaceCv(
            @PathVariable("cvId") Long cvId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "titre", required = false) String titre) throws IOException, CvNotFoundException {
        return ResponseEntity.ok(cvService.replaceCv(cvId, file, titre));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadCv(@PathVariable("id") Long id) throws IOException {
        Resource file = cvService.downloadCv(id);

        String fileName = "cv_" + id + ".pdf"; // tu peux améliorer ce nom avec le vrai nom de fichier si besoin

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    // 🔹 Supprimer un CV
    @DeleteMapping("/{cvId}")
    public ResponseEntity<Void> deleteCv(@PathVariable("cvId") Long cvId) throws CvNotFoundException {
        cvService.deleteCv(cvId);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Récupérer tous les CVs d’un candidat
    @GetMapping("/{candidatId}")
    public ResponseEntity<List<CvDTO>> getCvsByCandidat(@PathVariable("candidatId") Long candidatId) throws CandidatNotFoundException {
        return ResponseEntity.ok(cvService.getCvsByCandidat(candidatId));
    }

}
