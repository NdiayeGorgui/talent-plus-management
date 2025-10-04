package com.gogo.candidat_service.repository;

import com.gogo.candidat_service.model.CompetenceLinguistique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CompetenceLinguistiqueRepository extends JpaRepository<CompetenceLinguistique,Long> {
    Collection<CompetenceLinguistique> findByCandidatId(Long candidatId);
}
