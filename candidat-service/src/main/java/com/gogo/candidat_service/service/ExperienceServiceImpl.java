package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.ExperienceDTO;
import com.gogo.candidat_service.mapper.ExperienceMapper;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Experience;
import com.gogo.candidat_service.repository.CandidatRepository;
import com.gogo.candidat_service.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final CandidatRepository candidatRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, CandidatRepository candidatRepository) {
        this.experienceRepository = experienceRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public ExperienceDTO addExperience(Long candidatId, ExperienceDTO dto) {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec id " + candidatId));

        Experience experience = ExperienceMapper.fromDTO(dto, candidat);

        Experience saved = experienceRepository.save(experience);
        return ExperienceMapper.toDTO(saved);
    }

    @Override
    public List<ExperienceDTO> getExperiencesByCandidat(Long candidatId) {
        return experienceRepository.findByCandidatId(candidatId).stream()
                .map(ExperienceMapper::toDTO)
                .toList();
    }


    @Override
    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}

