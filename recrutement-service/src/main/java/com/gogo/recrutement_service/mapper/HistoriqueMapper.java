package com.gogo.recrutement_service.mapper;


import com.gogo.recrutement_service.dto.HistoriqueDTO;
import com.gogo.recrutement_service.model.Historique;

public class HistoriqueMapper {
    public static HistoriqueDTO toDTO(Historique entity) {
        HistoriqueDTO dto = new HistoriqueDTO();
        dto.setId(entity.getId());
        dto.setProcessusId(entity.getProcessusId());
        dto.setStatut(entity.getStatut());
        dto.setDateChangement(entity.getDateChangement());
        dto.setRecruteur(entity.getRecruteur());
        return dto;
    }
}

