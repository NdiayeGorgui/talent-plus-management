package com.gogo.recrutement_service.model;

import com.gogo.recrutement_service.enums.TypeCandidature;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "processus")
public class Processus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long candidatId;

    @Column(nullable = true)
    private Long offreId; // null si candidature spontanée

    @Enumerated(EnumType.STRING)
    private TypeCandidature typeCandidature; // OFFRE ou SPONTANEE

    private String statut; // RECU, EN_ETUDE, ENTRETIEN, PROPOSITION, ACCEPTE, REFUSE, etc.
    private LocalDateTime dateMaj;
}
