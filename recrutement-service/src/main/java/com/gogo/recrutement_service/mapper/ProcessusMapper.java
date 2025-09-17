package com.gogo.recrutement_service.mapper;

import com.gogo.recrutement_service.dto.ProcessusDTO;
import com.gogo.recrutement_service.model.Processus;

public class ProcessusMapper {
    public static ProcessusDTO toDTO(Processus entity) {
        ProcessusDTO dto = new ProcessusDTO();
        dto.setId(entity.getId());
        dto.setCandidatId(entity.getCandidatId());
        dto.setOffreId(entity.getOffreId());
        dto.setStatut(entity.getStatut());
        dto.setDateMaj(entity.getDateMaj());
        return dto;
    }

    public static Processus fromDTO(ProcessusDTO dto) {
        Processus entity = new Processus();
        entity.setId(dto.getId());
        entity.setCandidatId(dto.getCandidatId());
        entity.setOffreId(dto.getOffreId());
        entity.setStatut(dto.getStatut());
        entity.setDateMaj(dto.getDateMaj());
        return entity;
    }
}
