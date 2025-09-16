package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.CompetenceDTO;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Competence;

public class CompetenceMapper {

    public static Competence fromDTO(CompetenceDTO dto, Candidat candidat) {
        Competence comp = new Competence();
        comp.setLibelle(dto.getLibelle());
        comp.setNiveau(dto.getNiveau()); // si dto contient déjà l'enum Niveau, sinon adapter
        comp.setCandidat(candidat);
        return comp;
    }

    public static CompetenceDTO toDTO(Competence competence) {
        CompetenceDTO dto = new CompetenceDTO();
        dto.setId(competence.getId());
        dto.setLibelle(competence.getLibelle());
        dto.setNiveau(competence.getNiveau());
        return dto;
    }
}
