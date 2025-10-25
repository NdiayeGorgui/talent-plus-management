package com.gogo.recruteur_service.controller;

import com.gogo.recruteur_service.dto.AdminDTO;
import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.exception.AdminNotFoundException;
import com.gogo.recruteur_service.exception.RecruteurNotFoundException;
import com.gogo.recruteur_service.service.RecruteurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    public ResponseEntity<RecruteurDTO> getRecruteurById(@PathVariable("id") Long id) throws RecruteurNotFoundException {
        return ResponseEntity.ok(recruteurService.getRecruteurById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruteurDTO> updateRecruteur(@PathVariable("id") Long id, @RequestBody RecruteurDTO dto) throws RecruteurNotFoundException {
        return ResponseEntity.ok(recruteurService.updateRecruteur(id, dto));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable("id") Long id, @RequestBody AdminDTO dto) throws AdminNotFoundException {
        return ResponseEntity.ok(recruteurService.updateAdmin(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruteur(@PathVariable("id") Long id) throws RecruteurNotFoundException {
        recruteurService.deleteRecruteur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<RecruteurDTO> findByEmail(@PathVariable("email") String email) {
        Optional<RecruteurDTO> recruteur = recruteurService.findByEmail(email);
        return recruteur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/admin/email/{email}")
    public ResponseEntity<AdminDTO> findByAdminEmail(@PathVariable("email") String email) {
        Optional<AdminDTO> admin = recruteurService.findByAdminEmail(email);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
   // qui donne l’id du recruteur → pour appeler ensuite l’API /recruteur/{id}/candidats.
    @GetMapping("/email/{email}/id")
    public ResponseEntity<Long> getRecruteurIdByEmail(@PathVariable("email") String email) {
        RecruteurDTO r = recruteurService.findByEmail(email)
                .orElseThrow();
        return ResponseEntity.ok(r.getId());
    }


}

