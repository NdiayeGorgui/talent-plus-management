package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.*;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Disponibilite;

public class CandidatMapper {

    public static Candidat fromRequest(PostulerRequest request) {
        Candidat candidat = new Candidat();
        candidat.setNom(request.getNom());
        candidat.setPrenom(request.getPrenom());
        candidat.setEmail(request.getEmail());
        candidat.setTelephone(request.getTelephone());
        candidat.setDateNaissance(request.getDateNaissance());
        candidat.setAdresse(request.getAdresse());
        candidat.setDisponibilite(
                request.getDisponibilite() != null
                        ? Disponibilite.valueOf(request.getDisponibilite())
                        : Disponibilite.DISPONIBLE
        );
        return candidat;
    }

    public static CandidatDTO toDTO(Candidat c) {
        CandidatDTO dto = new CandidatDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setPrenom(c.getPrenom());
        dto.setEmail(c.getEmail());
        dto.setTelephone(c.getTelephone());
        dto.setDateNaissance(c.getDateNaissance());
        dto.setAdresse(c.getAdresse());
        dto.setDisponibilite(c.getDisponibilite() != null ? c.getDisponibilite().name() : null);
        return dto;
    }
    public static CandidatResponseDTO toResponseDTO(Candidat candidat) {
        CandidatResponseDTO dto = new CandidatResponseDTO();
        dto.setId(candidat.getId());
        dto.setNom(candidat.getNom());
        dto.setPrenom(candidat.getPrenom());
        dto.setEmail(candidat.getEmail());
        dto.setTelephone(candidat.getTelephone());
        dto.setDateNaissance(candidat.getDateNaissance());
        dto.setAdresse(candidat.getAdresse());
        dto.setDisponibilite(candidat.getDisponibilite().name());

        // ✅ CVs complets
        dto.setCvs(candidat.getCvs().stream()
                .map(cv -> {
                    CvDTO c = new CvDTO();
                    c.setId(cv.getId());
                    c.setTitre(cv.getTitre());
                    c.setFichierUrl(cv.getFichierUrl());
                    c.setVersion(cv.getVersion());
                    c.setDateDepot(cv.getDateDepot());
                    return c;
                }).toList());

        // ✅ Lettres complètes
        dto.setLettres(candidat.getLettres().stream()
                .map(lettre -> {
                    LettreDTO l = new LettreDTO();
                    l.setId(lettre.getId());
                    l.setTitre(lettre.getTitre());
                    l.setContenu(lettre.getContenu());
                    l.setFichierUrl(lettre.getFichierUrl());
                    l.setVersion(lettre.getVersion());
                    l.setDateDepot(lettre.getDateDepot());
                    return l;
                }).toList());

        // ✅ Expériences complètes
        dto.setExperiences(candidat.getExperiences().stream()
                .map(exp -> {
                    ExperienceDTO e = new ExperienceDTO();
                    e.setId(exp.getId());
                    e.setPoste(exp.getPoste());
                    e.setEntreprise(exp.getEntreprise());
                    e.setDateDebut(exp.getDateDebut());
                    e.setDateFin(exp.getDateFin());
                    e.setDescription(exp.getDescription());
                    return e;
                }).toList());

        // ✅ Compétences complètes
        dto.setCompetences(candidat.getCompetences().stream()
                .map(comp -> {
                    CompetenceDTO c = new CompetenceDTO();
                    c.setId(comp.getId());
                    c.setLibelle(comp.getLibelle());
                    c.setNiveau(comp.getNiveau()); // si enum => comp.getNiveau().name()
                    return c;
                }).toList());

        return dto;
    }

}
