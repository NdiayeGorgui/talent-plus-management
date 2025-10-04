package com.gogo.offre_emploi_service.mapper;


import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.model.Offre;

import java.time.LocalDateTime;

public class OffreMapper {

    public static OffreDTO toDTO(Offre offre) {
        OffreDTO dto = new OffreDTO();
        dto.setId(offre.getId());
        dto.setTitre(offre.getTitre());
        dto.setDescription(offre.getDescription());
        dto.setDatePublication(offre.getDatePublication());
        dto.setDateFinAffichage(offre.getDateFinAffichage());
        dto.setCategorie(offre.getCategorie());
        dto.setVille(offre.getVille());
        dto.setPays(offre.getPays());
        dto.setActive(offre.isActive());
        dto.setRecruteurId(offre.getRecruteurId());
        return dto;
    }

    public static Offre fromDTO(OffreDTO dto) {
        Offre offre = new Offre();
       // offre.setId(dto.getId());
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setDatePublication(dto.getDatePublication() != null ? dto.getDatePublication() : LocalDateTime.now());
        offre.setDateFinAffichage(dto.getDateFinAffichage());
        offre.setCategorie(dto.getCategorie());
        offre.setVille(dto.getVille());
        offre.setPays(dto.getPays());
        offre.setActive(dto.isActive());
        offre.setRecruteurId(dto.getRecruteurId());
        return offre;
    }
}
