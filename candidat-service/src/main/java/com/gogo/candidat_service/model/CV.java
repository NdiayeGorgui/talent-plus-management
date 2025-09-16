package com.gogo.candidat_service.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cvs")
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String fichierUrl; // chemin ou URL du CV (ex: stockage local / S3)
    private LocalDateTime dateDepot = LocalDateTime.now();
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    @JsonBackReference
    private Candidat candidat;


}

