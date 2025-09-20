package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.ExperienceDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.ExperienceNotFoundException;
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
    public List<ExperienceDTO> addExperiences(Long candidatId, List<ExperienceDTO> dtos) throws CandidatNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        List<Experience> experiences = dtos.stream()
                .map(dto -> ExperienceMapper.fromDTO(dto, candidat))
                .toList();

        List<Experience> savedExperiences = experienceRepository.saveAll(experiences);

        return savedExperiences.stream()
                .map(ExperienceMapper::toDTO)
                .toList();
    }

    @Override
    public List<ExperienceDTO> getExperiencesByCandidat(Long candidatId) throws CandidatNotFoundException {
        if (!candidatRepository.existsById(candidatId)) {
            throw new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId);
        }

        return experienceRepository.findByCandidatId(candidatId)
                .stream()
                .map(ExperienceMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteExperience(Long id) throws ExperienceNotFoundException {
        if (!experienceRepository.existsById(id)) {
            throw new ExperienceNotFoundException("Expérience non trouvée avec id " + id);
        }
        experienceRepository.deleteById(id);
    }
}
