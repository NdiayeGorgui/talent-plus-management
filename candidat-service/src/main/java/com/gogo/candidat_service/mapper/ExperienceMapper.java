package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.ExperienceDTO;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Experience;

public class ExperienceMapper {

    public static Experience fromDTO(ExperienceDTO dto, Candidat candidat) {
        Experience exp = new Experience();
        exp.setPoste(dto.getPoste());
        exp.setEntreprise(dto.getEntreprise());
        exp.setDateDebut(dto.getDateDebut());
        exp.setDateFin(dto.getDateFin());
        exp.setDescription(dto.getDescription());
        exp.setCandidat(candidat);
        return exp;
    }

    public static ExperienceDTO toDTO(Experience exp) {
        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(exp.getId());
        dto.setPoste(exp.getPoste());
        dto.setEntreprise(exp.getEntreprise());
        dto.setDateDebut(exp.getDateDebut());
        dto.setDateFin(exp.getDateFin());
        dto.setDescription(exp.getDescription());
        return dto;
    }
}

