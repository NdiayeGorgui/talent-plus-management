package com.gogo.recruteur_service.service;

import com.gogo.recruteur_service.dto.AdminDTO;
import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.exception.AdminNotFoundException;
import com.gogo.recruteur_service.exception.RecruteurNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RecruteurService {
    RecruteurDTO addRecruteur(RecruteurDTO dto);
    List<RecruteurDTO> getAllRecruteurs();
    RecruteurDTO getRecruteurById(Long id) throws RecruteurNotFoundException;
    RecruteurDTO updateRecruteur(Long id, RecruteurDTO dto) throws RecruteurNotFoundException;
    AdminDTO updateAdmin(Long id, AdminDTO dto) throws AdminNotFoundException;
    void deleteRecruteur(Long id) throws RecruteurNotFoundException;

    Optional<RecruteurDTO> findByEmail(String email);
    Optional<AdminDTO> findByAdminEmail(String email);
}

