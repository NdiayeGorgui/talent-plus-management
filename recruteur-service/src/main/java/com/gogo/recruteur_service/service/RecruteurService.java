package com.gogo.recruteur_service.service;

import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.exception.RecruteurNotFoundException;

import java.util.List;

public interface RecruteurService {
    RecruteurDTO addRecruteur(RecruteurDTO dto);
    List<RecruteurDTO> getAllRecruteurs();
    RecruteurDTO getRecruteurById(Long id) throws RecruteurNotFoundException;
    RecruteurDTO updateRecruteur(Long id, RecruteurDTO dto) throws RecruteurNotFoundException;
    void deleteRecruteur(Long id) throws RecruteurNotFoundException;
}

