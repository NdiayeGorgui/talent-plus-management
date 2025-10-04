package com.gogo.candidat_service.mapper;


import com.gogo.candidat_service.dto.CompetenceLinguistiqueDTO;
import com.gogo.candidat_service.enums.Langue;
import com.gogo.candidat_service.enums.Niveau;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.CompetenceLinguistique;

public class CompetenceLinguistiqueMapper {

    // ✅ Convertir un DTO en entité
    public static CompetenceLinguistique fromDTO(CompetenceLinguistiqueDTO dto, Candidat candidat) {
        CompetenceLinguistique cl = new CompetenceLinguistique();
        if (dto.getLangue() != null) {
            cl.setLangue(Langue.valueOf(dto.getLangue().toUpperCase())); // ex: "FR" ou "EN"
        }
        if (dto.getNiveau() != null) {
            cl.setNiveau(Niveau.valueOf(dto.getNiveau().toUpperCase())); // DEBUTANT, INTERMEDIAIRE, EXPERT
        }
        cl.setCandidat(candidat);
        return cl;
    }

    // ✅ Convertir une entité en DTO
    public static CompetenceLinguistiqueDTO toDTO(CompetenceLinguistique competenceLinguistique) {
        CompetenceLinguistiqueDTO dto = new CompetenceLinguistiqueDTO();
        dto.setId(competenceLinguistique.getId());
        dto.setLangue(competenceLinguistique.getLangue() != null ? competenceLinguistique.getLangue().name() : null);
        dto.setNiveau(competenceLinguistique.getNiveau() != null ? competenceLinguistique.getNiveau().name() : null);
        return dto;
    }
}

