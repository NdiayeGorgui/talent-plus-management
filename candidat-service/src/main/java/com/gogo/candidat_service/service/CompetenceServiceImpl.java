package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.dto.CompetenceFrequencyDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CompetenceNotFoundException;
import com.gogo.candidat_service.mapper.CompetenceMapper;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Competence;
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
    public List<CompetenceDTO> addCompetences(Long candidatId, List<CompetenceDTO> dtos) throws CandidatNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        List<Competence> competences = dtos.stream()
                .map(dto -> CompetenceMapper.fromDTO(dto, candidat))
                .toList();

        List<Competence> savedCompetences = competenceRepository.saveAll(competences);

        return savedCompetences.stream()
                .map(CompetenceMapper::toDTO)
                .toList();
    }

    @Override
    public List<CompetenceDTO> getCompetencesByCandidat(Long candidatId) throws CandidatNotFoundException {
        if (!candidatRepository.existsById(candidatId)) {
            throw new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId);
        }

        return competenceRepository.findByCandidatId(candidatId)
                .stream()
                .map(CompetenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompetence(Long id) throws CompetenceNotFoundException {
        if (!competenceRepository.existsById(id)) {
            throw new CompetenceNotFoundException("Compétence non trouvée avec id " + id);
        }
        competenceRepository.deleteById(id);
    }

    @Override
    public List<CompetenceFrequencyDTO> findMostFrequentCompetences() {
        return competenceRepository.findAll().stream()
                .collect(Collectors.groupingBy(Competence::getLibelle, Collectors.counting()))
                .entrySet().stream()
                .map(e -> new CompetenceFrequencyDTO(e.getKey(), e.getValue()))
                .sorted((a, b) -> Long.compare(b.getFrequency(), a.getFrequency()))
                .collect(Collectors.toList());
    }
}
