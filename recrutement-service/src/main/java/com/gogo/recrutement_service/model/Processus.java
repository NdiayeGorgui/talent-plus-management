package com.gogo.recrutement_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "processus")
public class Processus {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long candidatId;
    private Long offreId;

    private String statut; // RECU, EN_ETUDE, ENTRETIEN, PROPOSITION, ACCEPTE, REFUSE
    private LocalDateTime dateMaj;

}
