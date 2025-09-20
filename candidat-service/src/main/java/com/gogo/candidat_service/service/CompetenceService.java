package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.dto.CompetenceFrequencyDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CompetenceNotFoundException;
import com.gogo.candidat_service.model.Competence;
import com.gogo.candidat_service.model.Niveau;

import java.util.List;

public interface CompetenceService {
    List<CompetenceDTO> addCompetences(Long candidatId, List<CompetenceDTO> dtos) throws CandidatNotFoundException;
    List<CompetenceDTO> getCompetencesByCandidat(Long candidatId) throws CandidatNotFoundException;
    void deleteCompetence(Long id) throws CompetenceNotFoundException;
    List<CompetenceFrequencyDTO> findMostFrequentCompetences();
}
