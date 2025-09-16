package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.mapper.CompetenceMapper;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Competence;
import com.gogo.candidat_service.model.Niveau;
import com.gogo.candidat_service.repository.CandidatRepository;
import com.gogo.candidat_service.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetenceServiceImpl implements CompetenceService {

    private final CompetenceRepository competenceRepository;
    private final CandidatRepository candidatRepository;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CandidatRepository candidatRepository) {
        this.competenceRepository = competenceRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public CompetenceDTO addCompetence(Long candidatId, CompetenceDTO dto) {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec id " + candidatId));

        Competence competence = CompetenceMapper.fromDTO(dto, candidat);
        Competence saved = competenceRepository.save(competence);

        return CompetenceMapper.toDTO(saved);
    }

    @Override
    public List<CompetenceDTO> getCompetencesByCandidat(Long candidatId) {
        return competenceRepository.findByCandidatId(candidatId)
                .stream()
                .map(CompetenceMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }
}
