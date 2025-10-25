package com.gogo.offre_emploi_service.service;

import com.gogo.offre_emploi_service.dto.OffreCountDTO;
import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.exception.OffreNotFoundException;

import java.util.List;

public interface OffreService {

    OffreDTO createOffre(OffreDTO dto);

    OffreDTO updateOffre(Long id, OffreDTO dto) throws OffreNotFoundException;
    void deleteOffre(Long id) throws OffreNotFoundException;

    void closeOffre(Long id) throws OffreNotFoundException;
    void openOffre(Long id) throws OffreNotFoundException;

    List<OffreDTO> getAllOffres();

    OffreDTO getOffreById(Long id) throws OffreNotFoundException;
    List<OffreDTO> getOffresByRecruteur(Long recruteurId);
    List<Long> findOffreIdsByRecruteurId(Long recruteurId);
    List<OffreDTO> getOffresByEmployeur(Long employeurId);
    List<Long> findOffreIdsByEmployeurId(Long employeurId);
    List<OffreCountDTO> countOffresByRecruteur();
    List<OffreCountDTO> countOffresByEmployeur();

}

