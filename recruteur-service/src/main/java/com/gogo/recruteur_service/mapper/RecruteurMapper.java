package com.gogo.recruteur_service.mapper;

import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.model.Recruteur;

public class RecruteurMapper {

    public static Recruteur fromDTO(RecruteurDTO dto) {
        Recruteur recruteur = new Recruteur();
       // recruteur.setId(dto.getId()); // optionnel si auto-généré
        recruteur.setNom(dto.getNom());
        recruteur.setPrenom(dto.getPrenom());
        recruteur.setEmail(dto.getEmail());
        recruteur.setTelephone(dto.getTelephone());
        recruteur.setPoste(dto.getPoste());
        recruteur.setNiveau(dto.getNiveau());

        return recruteur;
    }

    public static RecruteurDTO toDTO(Recruteur recruteur) {
        RecruteurDTO dto = new RecruteurDTO();
        dto.setId(recruteur.getId());
        dto.setNom(recruteur.getNom());
        dto.setPrenom(recruteur.getPrenom());
        dto.setEmail(recruteur.getEmail());
        dto.setTelephone(recruteur.getTelephone());
        dto.setPoste(recruteur.getPoste());
        dto.setNiveau(recruteur.getNiveau());
        return dto;
    }
}

