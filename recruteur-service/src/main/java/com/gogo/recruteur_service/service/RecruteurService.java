package com.gogo.recruteur_service.service;

import com.gogo.recruteur_service.dto.RecruteurDTO;

import java.util.List;

public interface RecruteurService {
    RecruteurDTO addRecruteur(RecruteurDTO dto);
    List<RecruteurDTO> getAllRecruteurs();
    RecruteurDTO getRecruteurById(Long id);
    RecruteurDTO updateRecruteur(Long id, RecruteurDTO dto);
    void deleteRecruteur(Long id);
}

