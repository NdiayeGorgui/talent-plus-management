package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.ExperienceDTO;
import com.gogo.candidat_service.model.Experience;

import java.util.List;

public interface ExperienceService {
    ExperienceDTO addExperience(Long candidatId, ExperienceDTO experience);
    List<ExperienceDTO> getExperiencesByCandidat(Long candidatId);
    void deleteExperience(Long id);
}

