package com.gogo.candidat_service.model;

import com.gogo.candidat_service.enums.Langue;
import com.gogo.candidat_service.enums.Niveau;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "competences_linguistiques")
public class CompetenceLinguistique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Langue langue; // FR, EN, ES, etc.

    @Enumerated(EnumType.STRING)
    private Niveau niveau; // DEBUTANT, INTERMEDIAIRE, EXPERT

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;
}
