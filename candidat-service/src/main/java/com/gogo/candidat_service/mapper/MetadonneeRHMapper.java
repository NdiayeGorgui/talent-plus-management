package com.gogo.candidat_service.mapper;

import com.gogo.candidat_service.dto.MetadonneeRHDTO;
import com.gogo.candidat_service.enums.Disponibilite;
import com.gogo.candidat_service.enums.Source;
import com.gogo.candidat_service.model.MetadonneeRH;

public class MetadonneeRHMapper {

    public static MetadonneeRHDTO toDTO(MetadonneeRH entity) {
        if (entity == null) return null;

        MetadonneeRHDTO dto = new MetadonneeRHDTO();
        dto.setId(entity.getId());
        dto.setDomaineRecherche(entity.getDomaineRecherche());
        dto.setTypeContrat(entity.getTypeContrat());
        dto.setLocalisation(entity.getLocalisation());
        dto.setDisponibilite(entity.getDisponibilite() != null ? entity.getDisponibilite().name() : null);
        dto.setPretentionsSalariales(entity.getPretentionsSalariales());
        dto.setSource(String.valueOf(entity.getSource()));
        return dto;
    }

    public static MetadonneeRH fromDTO(MetadonneeRHDTO dto) {
        if (dto == null) return null;

        MetadonneeRH entity = new MetadonneeRH();
        entity.setId(dto.getId());
        entity.setDomaineRecherche(dto.getDomaineRecherche());
        entity.setTypeContrat(dto.getTypeContrat());
        entity.setLocalisation(dto.getLocalisation());
        if (dto.getDisponibilite() != null) {
            entity.setDisponibilite(Enum.valueOf(
                    Disponibilite.class, dto.getDisponibilite()
            ));
        }
        entity.setPretentionsSalariales(dto.getPretentionsSalariales());
        entity.setSource(Source.valueOf(dto.getSource()));
        return entity;
    }
}
