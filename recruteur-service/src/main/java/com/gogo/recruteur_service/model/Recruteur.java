package com.gogo.recruteur_service.model;


import com.gogo.recruteur_service.enums.Niveau;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "recruteurs")
public class Recruteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String poste;
    private Niveau niveau;

}
