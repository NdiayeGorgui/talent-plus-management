package com.gogo.recrutement_service.mapper;

import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.enums.StatutProcessus;
import com.gogo.recrutement_service.model.Processus;
public class ProcessusMapper {
    public static ProcessusDTO toDTO(Processus entity) {
        ProcessusDTO dto = new ProcessusDTO();
        dto.setId(entity.getId());
        dto.setCandidatId(entity.getCandidatId());
        dto.setOffreId(entity.getOffreId());
        dto.setTypeCandidature(entity.getTypeCandidature());

        // Conversion de String -> Enum (si entity.getStatut() est un String)
        if (entity.getStatut() != null) {
            dto.setStatut(StatutProcessus.valueOf(entity.getStatut())); // Correction ici
        }

        dto.setDateMaj(entity.getDateMaj());
        return dto;
    }

    public static Processus fromDTO(ProcessusDTO dto) {
        Processus entity = new Processus();
        entity.setId(dto.getId());
        entity.setCandidatId(dto.getCandidatId());
        entity.setOffreId(dto.getOffreId());
        entity.setTypeCandidature(dto.getTypeCandidature());
        entity.setStatut(dto.getStatut().name()); // ✅ OK si les deux sont du type StatutProcessus
        entity.setDateMaj(dto.getDateMaj());
        return entity;
    }

}
