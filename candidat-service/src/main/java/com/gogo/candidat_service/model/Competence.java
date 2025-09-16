package com.gogo.candidat_service.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "competences")
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;


}

