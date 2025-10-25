package com.gogo.employeur_service.mapper;


import com.gogo.employeur_service.dto.EmployeurDTO;
import com.gogo.employeur_service.model.Employeur;

public class EmployeurMapper {

    public static EmployeurDTO toDTO(Employeur entity) {
        if (entity == null) return null;

        EmployeurDTO dto = new EmployeurDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setTypeEntreprise(entity.getTypeEntreprise());
        dto.setEmailContact(entity.getEmailContact());
        dto.setTelephone(entity.getTelephone());
        dto.setPoste(entity.getPoste());
        dto.setAdresse(entity.getAdresse());
        dto.setVille(entity.getVille());
        dto.setPays(entity.getPays());

        return dto;
    }

    public static Employeur fromDTO(EmployeurDTO dto) {
        if (dto == null) return null;

        Employeur entity = new Employeur();
        entity.setId(dto.getId());
        entity.setNom(dto.getNom());
        entity.setTypeEntreprise(dto.getTypeEntreprise());
        entity.setEmailContact(dto.getEmailContact());
        entity.setTelephone(dto.getTelephone());
        entity.setPoste(dto.getPoste());
        entity.setAdresse(dto.getAdresse());
        entity.setVille(dto.getVille());
        entity.setPays(dto.getPays());

        return entity;
    }
}

