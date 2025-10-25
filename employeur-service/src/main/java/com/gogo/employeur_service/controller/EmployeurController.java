package com.gogo.employeur_service.controller;

import com.gogo.employeur_service.dto.EmployeurDTO;
import com.gogo.employeur_service.exception.EmployeurNotFoundException;
import com.gogo.employeur_service.service.EmployeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employeurs")
@RequiredArgsConstructor
public class EmployeurController {

    private final EmployeurService service;

    @PostMapping
    public ResponseEntity<EmployeurDTO> create(@RequestBody EmployeurDTO dto) {
        return ResponseEntity.ok(service.createEmployeur(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeurDTO> getById(@PathVariable("id") Long id) throws EmployeurNotFoundException {
        return ResponseEntity.ok(service.getEmployeurById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeurDTO>> getAll() {
        return ResponseEntity.ok(service.getAllEmployeurs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeurDTO> update(@PathVariable("id") Long id, @RequestBody EmployeurDTO dto) throws EmployeurNotFoundException {
        return ResponseEntity.ok(service.updateEmployeur(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws EmployeurNotFoundException {
        service.deleteEmployeur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}/id")
    public ResponseEntity<Long> getEmployeurIdByEmail(@PathVariable("email") String email) throws EmployeurNotFoundException {
        EmployeurDTO e = service.findByEmail(email)
                .orElseThrow(() -> new EmployeurNotFoundException("Employeur non trouvé pour l’email : " + email));
        return ResponseEntity.ok(e.getId());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeurDTO> getByEmail(@PathVariable("email") String email) throws EmployeurNotFoundException {
        EmployeurDTO employeur = service.findByEmail(email)
                .orElseThrow(() -> new EmployeurNotFoundException("Employeur non trouvé pour l’email : " + email));

        return ResponseEntity.ok(employeur);
    }


}

