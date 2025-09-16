package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.PostulerRequest;
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
    public CandidatDTO updateCandidat(Long id, Candidat candidat) {
        return candidatRepository.findById(id)
                .map(existing -> {
                    existing.setNom(candidat.getNom());
                    existing.setPrenom(candidat.getPrenom());
                    existing.setEmail(candidat.getEmail());
                    existing.setTelephone(candidat.getTelephone());
                    existing.setAdresse(candidat.getAdresse());
                    existing.setDateNaissance(candidat.getDateNaissance());
                    Candidat updated = candidatRepository.save(existing);
                    return CandidatMapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec id " + id));
    }

    @Override
    public void deleteCandidat(Long id) {
        candidatRepository.deleteById(id);
    }

    @Override
    public CandidatDTO getCandidatById(Long id) {
        return candidatRepository.findById(id)
                .map(CandidatMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec id " + id));
    }


    @Override
    public CandidatDTO getCandidatByEmail(String email) {
        return candidatRepository.findByEmail(email)
                .map(CandidatMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé avec email " + email));
    }


    @Override
    public List<CandidatDTO> getAllCandidats() {
        return candidatRepository.findAll()
                .stream()
                .map(CandidatMapper::toDTO)
                .toList();
    }


    @Override
    public List<CandidatDTO> searchCandidats(String keyword) {
        return candidatRepository
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(CandidatMapper::toDTO) // conversion vers DTO
                .toList();
    }


    @Override
    public Candidat postuler(PostulerRequest request) {
        // 1. Mapper le candidat
        Candidat candidat = CandidatMapper.fromRequest(request);

        // 2. Mapper expériences
        if (request.getExperiences() != null) {
            request.getExperiences().forEach(dto ->
                    candidat.getExperiences().add(ExperienceMapper.fromDTO(dto, candidat))
            );
        }

        // 3. Mapper compétences
        if (request.getCompetences() != null) {
            request.getCompetences().forEach(dto ->
                    candidat.getCompetences().add(CompetenceMapper.fromDTO(dto, candidat))
            );
        }

        // 4. Mapper lettres
        if (request.getLettres() != null) {
            request.getLettres().forEach(dto ->
                    candidat.getLettres().add(LettreMapper.fromDTO(dto, candidat))
            );
        }

        // 5. Mapper CV
        if (request.getCvs() != null) {
            request.getCvs().forEach(dto ->
                    candidat.getCvs().add(CvMapper.fromDTO(dto, candidat))
            );
        }

        return candidatRepository.save(candidat);
    }

}

