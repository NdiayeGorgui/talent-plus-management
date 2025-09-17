package com.gogo.recruteur_service.mapper;

import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.model.Recruteur;

public class RecruteurMapper {

    public static Recruteur fromDTO(RecruteurDTO dto) {
        Recruteur recruteur = new Recruteur();
        recruteur.setId(dto.getId()); // optionnel si auto-généré
        recruteur.setNom(dto.getNom());
        recruteur.setEmail(dto.getEmail());
        return recruteur;
    }

    public static RecruteurDTO toDTO(Recruteur recruteur) {
        RecruteurDTO dto = new RecruteurDTO();
        dto.setId(recruteur.getId());
        dto.setNom(recruteur.getNom());
        dto.setEmail(recruteur.getEmail());
        return dto;
    }
}

