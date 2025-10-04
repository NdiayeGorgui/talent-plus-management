package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.CandidatResponseDTO;
import com.gogo.candidat_service.dto.PostulerRequest;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.mapper.*;
import com.gogo.candidat_service.model.*;
import com.gogo.candidat_service.repository.CandidatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidatServiceImpl implements CandidatService {

    private final CandidatRepository candidatRepository;

    public CandidatServiceImpl(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public CandidatDTO saveCandidat(Candidat candidat) {
        Candidat saved = candidatRepository.save(candidat);
        return CandidatMapper.toDTO(saved);
    }

    @Override
    public CandidatDTO updateCandidat(Long id, Candidat candidat) throws CandidatNotFoundException {
        return candidatRepository.findById(id)
                .map(existing -> {
                    existing.setNom(candidat.getNom());
                    existing.setPrenom(candidat.getPrenom());
                    existing.setEmail(candidat.getEmail());
                    existing.setTelephone(candidat.getTelephone());
                    existing.setAdresse(candidat.getAdresse());
                    existing.setDateNaissance(candidat.getDateNaissance());
                    existing.setNiveauEtude(candidat.getNiveauEtude());
                    Candidat updated = candidatRepository.save(existing);
                    return CandidatMapper.toDTO(updated);
                })
                .orElseThrow(() -> new CandidatNotFoundException("Candidate not available with id: " + id));
    }

    @Override
    public void deleteCandidat(Long id) throws CandidatNotFoundException {
        if (!candidatRepository.existsById(id)) {
            throw new CandidatNotFoundException("Candidate not available with id: " + id);
        }
        candidatRepository.deleteById(id);
    }

    @Override
    public CandidatResponseDTO getCandidatById(Long id) throws CandidatNotFoundException {
        return candidatRepository.findById(id)
                .map(CandidatMapper::toResponseDTO)
                .orElseThrow(() -> new CandidatNotFoundException("Candidate not available with id: " + id));
    }

    @Override
    public CandidatResponseDTO getCandidatByEmail(String email) throws CandidatNotFoundException {
        return candidatRepository.findByEmail(email)
                .map(CandidatMapper::toResponseDTO)
                .orElseThrow(() -> new CandidatNotFoundException("Candidate not available with email: " + email));
    }

    @Override
    public List<CandidatResponseDTO> getAllCandidats() {
        return candidatRepository.findAll()
                .stream()
                .map(CandidatMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CandidatResponseDTO> searchCandidats(String keyword) {
        return candidatRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(CandidatMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Candidat postuler(PostulerRequest request) {
        Candidat candidat = CandidatMapper.fromRequest(request);

        if (request.getExperiences() != null) {
            request.getExperiences().forEach(dto ->
                    candidat.getExperiences().add(ExperienceMapper.fromDTO(dto, candidat))
            );
        }

        if (request.getCompetences() != null) {
            request.getCompetences().forEach(dto ->
                    candidat.getCompetences().add(CompetenceMapper.fromDTO(dto, candidat))
            );
        }

        if (request.getLettres() != null) {
            request.getLettres().forEach(dto ->
                    candidat.getLettres().add(LettreMapper.fromDTO(dto, candidat))
            );
        }

        if (request.getCvs() != null) {
            request.getCvs().forEach(dto ->
                    candidat.getCvs().add(CvMapper.fromDTO(dto, candidat))
            );
        }

        return candidatRepository.save(candidat);
    }

    @Override
    public CandidatResponseDTO findById(Long id) throws CandidatNotFoundException {
        return candidatRepository.findById(id)
                .map(CandidatMapper::toResponseDTO)
                .orElseThrow(() -> new CandidatNotFoundException("Candidate not available with id: " + id));
    }
}
