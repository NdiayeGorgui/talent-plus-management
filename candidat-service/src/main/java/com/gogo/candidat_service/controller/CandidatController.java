package com.gogo.candidat_service.controller;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.CandidatureParMoisDTO;
import com.gogo.candidat_service.model.*;
import com.gogo.candidat_service.service.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public CandidatDTO updateCandidat(@PathVariable("id") Long id, @RequestBody Candidat candidat) {
        return candidatService.updateCandidat(id, candidat);
    }

    @DeleteMapping("/{id}")
    public void deleteCandidat(@PathVariable("id") Long id) {
        candidatService.deleteCandidat(id);
    }

    @GetMapping("/{id}")
    public CandidatDTO getCandidatById(@PathVariable("id") Long id) {
        return candidatService.getCandidatById(id);
    }

    @GetMapping("/email/{email}")
    public CandidatDTO getCandidatByEmail(@PathVariable("email") String email) {
        return candidatService.getCandidatByEmail(email);
    }

    @GetMapping
    public List<CandidatDTO> getAllCandidats() {
        return candidatService.getAllCandidats();
    }

    @GetMapping("/search")
    public List<CandidatDTO> searchCandidats(@RequestParam String keyword) {
        return candidatService.searchCandidats(keyword);
    }

    /*@GetMapping("/par-mois")
    public List<CandidatureParMoisDTO> getCandidaturesParMois() {
        return candidatService.getCandidaturesParMois();
    }*/


}

