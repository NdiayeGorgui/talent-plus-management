package com.gogo.recruteur_service.mapper;

import com.gogo.recruteur_service.dto.AdminDTO;
import com.gogo.recruteur_service.model.Admin;

public class AdminMapper {

    public static Admin fromDTO(AdminDTO dto) {
        Admin admin = new Admin();
       // recruteur.setId(dto.getId()); // optionnel si auto-généré
        admin.setNom(dto.getNom());
        admin.setPrenom(dto.getPrenom());
        admin.setEmail(dto.getEmail());
        admin.setTelephone(dto.getTelephone());
        admin.setPoste(dto.getPoste());


        return admin;
    }

    public static AdminDTO toDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setNom(admin.getNom());
        dto.setPrenom(admin.getPrenom());
        dto.setEmail(admin.getEmail());
        dto.setTelephone(admin.getTelephone());
        dto.setPoste(admin.getPoste());

        return dto;
    }
}

