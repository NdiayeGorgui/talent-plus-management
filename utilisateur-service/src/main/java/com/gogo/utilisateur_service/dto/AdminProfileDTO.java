package com.gogo.utilisateur_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AdminProfileDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;
    private String role;

}
