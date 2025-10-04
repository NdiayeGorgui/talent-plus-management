package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.*;
import com.gogo.candidat_service.model.Candidat;

import java.util.stream.Collectors;

public class CandidatMapper {

    // 🔹 Conversion depuis la requête "postuler"
    public static Candidat fromRequest(PostulerRequest request) {
        Candidat candidat = new Candidat();
        candidat.setNom(request.getNom());
        candidat.setPrenom(request.getPrenom());
        candidat.setEmail(request.getEmail());
        candidat.setTelephone(request.getTelephone());
        candidat.setDateNaissance(request.getDateNaissance());
        candidat.setAdresse(request.getAdresse());
        return candidat;
    }

    // 🔹 Conversion entité -> CandidatDTO (données simples)
    public static CandidatDTO toDTO(Candidat c) {
        CandidatDTO dto = new CandidatDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setPrenom(c.getPrenom());
        dto.setEmail(c.getEmail());
        dto.setTelephone(c.getTelephone());
        dto.setDateNaissance(c.getDateNaissance());
        dto.setAdresse(c.getAdresse());
        dto.setNiveauEtude(c.getNiveauEtude() != null ? c.getNiveauEtude().name() : null);
        return dto;
    }

    // 🔹 Conversion complète pour affichage détaillé
    public static CandidatResponseDTO toResponseDTO(Candidat candidat) {
        CandidatResponseDTO dto = new CandidatResponseDTO();
        dto.setId(candidat.getId());
        dto.setNom(candidat.getNom());
        dto.setPrenom(candidat.getPrenom());
        dto.setEmail(candidat.getEmail());
        dto.setTelephone(candidat.getTelephone());
        dto.setDateNaissance(candidat.getDateNaissance());
        dto.setAdresse(candidat.getAdresse());
        dto.setNiveauEtude(candidat.getNiveauEtude() != null ? candidat.getNiveauEtude().name() : null);

        // ✅ Mapper les sous-collections en appelant les mappers spécialisés
        dto.setCvs(
                candidat.getCvs().stream()
                        .map(CvMapper::toDTO)
                        .collect(Collectors.toList())
        );

        dto.setLettres(
                candidat.getLettres().stream()
                        .map(LettreMapper::toDTO)
                        .collect(Collectors.toList())
        );

        dto.setExperiences(
                candidat.getExperiences().stream()
                        .map(ExperienceMapper::toDTO)
                        .collect(Collectors.toList())
        );

        dto.setCompetences(
                candidat.getCompetences().stream()
                        .map(CompetenceMapper::toDTO)
                        .collect(Collectors.toList())
        );

        dto.setCompetencesLinguistiques(
                candidat.getCompetencesLinguistiques().stream()
                        .map(CompetenceLinguistiqueMapper::toDTO)
                        .collect(Collectors.toList())
        );

        // ✅ Métadonnées RH
        dto.setMetadonneeRH(
                MetadonneeRHMapper.toDTO(candidat.getMetadonneeRH())
        );

        return dto;
    }
}
