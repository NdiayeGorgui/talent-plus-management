package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.PostulerRequest;
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
}
