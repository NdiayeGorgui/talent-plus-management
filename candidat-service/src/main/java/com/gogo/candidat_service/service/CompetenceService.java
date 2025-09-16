package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.model.Competence;
import com.gogo.candidat_service.model.Niveau;

import java.util.List;

public interface CompetenceService {
    CompetenceDTO addCompetence(Long candidatId, CompetenceDTO dto);
    List<CompetenceDTO> getCompetencesByCandidat(Long candidatId);
    void deleteCompetence(Long id);
}
