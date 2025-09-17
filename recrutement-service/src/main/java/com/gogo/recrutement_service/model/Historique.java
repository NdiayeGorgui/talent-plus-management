package com.gogo.recrutement_service.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "historiques")
public class Historique {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long processusId;
    private String statut;
    private LocalDateTime dateChangement;
    private String recruteur;

}

