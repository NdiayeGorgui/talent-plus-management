package com.gogo.offre_emploi_service.service;

import com.gogo.offre_emploi_service.dto.OffreDTO;

import java.util.List;

public interface OffreService {

    OffreDTO createOffre(OffreDTO dto);

    OffreDTO updateOffre(Long id, OffreDTO dto);

    void closeOffre(Long id);

    List<OffreDTO> getAllOffres();

    OffreDTO getOffreById(Long id);
    List<OffreDTO> getOffresByRecruteur(Long recruteurId);

}

