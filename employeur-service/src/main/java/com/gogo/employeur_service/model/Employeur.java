package com.gogo.employeur_service.model;

import com.gogo.employeur_service.enums.TypeEntreprise;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employeurs")
public class Employeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Enumerated(EnumType.STRING)
    private TypeEntreprise typeEntreprise;
    private String emailContact;
    private String telephone;
    private String poste;
    private String adresse;
    private String ville;
    private String pays;

}
