package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.CandidatResponseDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.model.*;
import com.gogo.candidat_service.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/candidats")
public class CandidatController {

    private final CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    @PostMapping
    public CandidatDTO createCandidat(@RequestBody Candidat candidat) {
        return candidatService.saveCandidat(candidat);
    }

    @PutMapping("/{id}")
    public CandidatDTO updateCandidat(@PathVariable("id") Long id, @RequestBody Candidat candidat) throws CandidatNotFoundException {
        return candidatService.updateCandidat(id, candidat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidat(@PathVariable("id") Long id) throws CandidatNotFoundException {
        candidatService.deleteCandidat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public CandidatResponseDTO getCandidatById(@PathVariable("id") Long id) throws CandidatNotFoundException {
        return candidatService.getCandidatById(id);
    }

    @GetMapping("/email/{email}")
    public CandidatResponseDTO getCandidatByEmail(@PathVariable("email") String email) throws CandidatNotFoundException {
        return candidatService.getCandidatByEmail(email);
    }

    @GetMapping
    public List<CandidatResponseDTO> getAllCandidats() {
        return candidatService.getAllCandidats();
    }

    @GetMapping("/search")
    public List<CandidatResponseDTO> searchCandidats(@RequestParam String keyword) {
        return candidatService.searchCandidats(keyword);
    }

    @GetMapping("/{id}/email")
    public ResponseEntity<String> getEmail(@PathVariable("id") Long id) throws CandidatNotFoundException {
        CandidatResponseDTO candidat = candidatService.findById(id);
        return ResponseEntity.ok(candidat.getEmail());
    }
}
