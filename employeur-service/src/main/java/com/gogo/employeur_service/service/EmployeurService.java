package com.gogo.employeur_service.service;

import com.gogo.employeur_service.dto.EmployeurDTO;
import com.gogo.employeur_service.exception.EmployeurNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeurService {
    EmployeurDTO createEmployeur(EmployeurDTO dto);
    EmployeurDTO getEmployeurById(Long id) throws EmployeurNotFoundException;
    List<EmployeurDTO> getAllEmployeurs();
    EmployeurDTO updateEmployeur(Long id, EmployeurDTO dto) throws EmployeurNotFoundException;
    void deleteEmployeur(Long id) throws EmployeurNotFoundException;
    Optional<EmployeurDTO> findByEmail(String email) throws EmployeurNotFoundException;
}

