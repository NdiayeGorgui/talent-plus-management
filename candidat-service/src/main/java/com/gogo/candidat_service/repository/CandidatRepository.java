package com.gogo.candidat_service.repository;

import com.gogo.candidat_service.model.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    // Recherche par email (unique)
    Optional<Candidat> findByEmail(String email);

    // Recherche par nom ou prénom
   List<Candidat> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
