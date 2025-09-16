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
        dto.setActive(offre.isActive());
        return dto;
    }

    public static Offre fromDTO(OffreDTO dto) {
        Offre offre = new Offre();
        offre.setId(dto.getId());
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setDatePublication(dto.getDatePublication() != null ? dto.getDatePublication() : LocalDateTime.now());
        offre.setActive(dto.isActive());
        return offre;
    }
}
