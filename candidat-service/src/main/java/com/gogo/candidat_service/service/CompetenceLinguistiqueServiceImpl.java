package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CompetenceLinguistiqueDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CompetenceLinguistiqueNotFoundException;
import com.gogo.candidat_service.mapper.CompetenceLinguistiqueMapper;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.CompetenceLinguistique;
import com.gogo.candidat_service.repository.CandidatRepository;
import com.gogo.candidat_service.repository.CompetenceLinguistiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetenceLinguistiqueServiceImpl implements CompetenceLinguistiqueService {

    private final CompetenceLinguistiqueRepository competenceLinguistiqueRepository;
    private final CandidatRepository candidatRepository;

    public CompetenceLinguistiqueServiceImpl(CompetenceLinguistiqueRepository competenceLinguistiqueRepository,
                                             CandidatRepository candidatRepository) {
        this.competenceLinguistiqueRepository = competenceLinguistiqueRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public List<CompetenceLinguistiqueDTO> addCompetencesLinguistiques(Long candidatId, List<CompetenceLinguistiqueDTO> dtos)
            throws CandidatNotFoundException {

        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        List<CompetenceLinguistique> competences = dtos.stream()
                .map(dto -> CompetenceLinguistiqueMapper.fromDTO(dto, candidat))
                .toList();

        List<CompetenceLinguistique> saved = competenceLinguistiqueRepository.saveAll(competences);

        return saved.stream()
                .map(CompetenceLinguistiqueMapper::toDTO)
                .toList();
    }

    @Override
    public List<CompetenceLinguistiqueDTO> getCompetencesLinguistiquesByCandidat(Long candidatId)
            throws CandidatNotFoundException {

        if (!candidatRepository.existsById(candidatId)) {
            throw new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId);
        }

        return competenceLinguistiqueRepository.findByCandidatId(candidatId)
                .stream()
                .map(CompetenceLinguistiqueMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompetenceLinguistique(Long id) throws CompetenceLinguistiqueNotFoundException {
        if (!competenceLinguistiqueRepository.existsById(id)) {
            throw new CompetenceLinguistiqueNotFoundException("Compétence linguistique non trouvée avec id " + id);
        }
        competenceLinguistiqueRepository.deleteById(id);
    }
}

