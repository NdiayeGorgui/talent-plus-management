package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CompetenceLinguistiqueDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CompetenceLinguistiqueNotFoundException;

import java.util.List;

public interface CompetenceLinguistiqueService {

    List<CompetenceLinguistiqueDTO> addCompetencesLinguistiques(Long candidatId, List<CompetenceLinguistiqueDTO> dtos)
            throws CandidatNotFoundException;

    List<CompetenceLinguistiqueDTO> getCompetencesLinguistiquesByCandidat(Long candidatId)
            throws CandidatNotFoundException;

    void deleteCompetenceLinguistique(Long id) throws CompetenceLinguistiqueNotFoundException;
    void updateCompetencesLinguistiques(Long candidatId, List<CompetenceLinguistiqueDTO> dtos)
            throws CandidatNotFoundException, CompetenceLinguistiqueNotFoundException;

}

