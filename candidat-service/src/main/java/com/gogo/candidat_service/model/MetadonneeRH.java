package com.gogo.candidat_service.model;

import com.gogo.candidat_service.enums.Disponibilite;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "metadonnees_rh")
public class MetadonneeRH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String domaineRecherche;       // IT, Marketing, Finance...
    private String typeContrat;           // CDI, CDD, Stage, Alternance...
    private String localisation;          // Ville, Région, Remote...

    @Enumerated(EnumType.STRING)
    private Disponibilite disponibilite;  // IMMÉDIATE, 1 MOIS, 3 MOIS...

    private Double pretentionsSalariales; // optionnel
    private String source;  //(site carrière, LinkedIn, recommandation, candidature directe, etc.)

    // Relation avec candidat
    @OneToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;
}
