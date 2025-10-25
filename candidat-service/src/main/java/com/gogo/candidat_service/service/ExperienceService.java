package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.ExperienceDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.ExperienceNotFoundException;

import java.util.List;

public interface ExperienceService {
    List<ExperienceDTO> addExperiences(Long candidatId, List<ExperienceDTO> experiences) throws CandidatNotFoundException;
    List<ExperienceDTO> getExperiencesByCandidat(Long candidatId) throws CandidatNotFoundException;
    void deleteExperience(Long id) throws ExperienceNotFoundException;
}

