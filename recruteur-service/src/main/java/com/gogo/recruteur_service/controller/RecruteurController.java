package com.gogo.recruteur_service.controller;

import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.service.RecruteurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recruteurs")
public class RecruteurController {

    private final RecruteurService recruteurService;

    public RecruteurController(RecruteurService recruteurService) {
        this.recruteurService = recruteurService;
    }

    @PostMapping
    public ResponseEntity<RecruteurDTO> addRecruteur(@RequestBody RecruteurDTO dto) {
        return ResponseEntity.ok(recruteurService.addRecruteur(dto));
    }

    @GetMapping
    public ResponseEntity<List<RecruteurDTO>> getAllRecruteurs() {
        return ResponseEntity.ok(recruteurService.getAllRecruteurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruteurDTO> getRecruteurById(@PathVariable Long id) {
        return ResponseEntity.ok(recruteurService.getRecruteurById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruteurDTO> updateRecruteur(@PathVariable Long id, @RequestBody RecruteurDTO dto) {
        return ResponseEntity.ok(recruteurService.updateRecruteur(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruteur(@PathVariable Long id) {
        recruteurService.deleteRecruteur(id);
        return ResponseEntity.noContent().build();
    }
}

