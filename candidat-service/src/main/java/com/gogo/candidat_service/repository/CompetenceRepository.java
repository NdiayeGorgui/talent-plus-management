package com.gogo.candidat_service.repository;

import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByCandidat(Candidat candidat);

    // Recherche par libellé
    List<Competence> findByLibelleContainingIgnoreCase(String libelle);

    List<Competence> findByCandidatId(Long candidatId);
}

