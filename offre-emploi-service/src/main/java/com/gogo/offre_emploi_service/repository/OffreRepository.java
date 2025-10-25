package com.gogo.offre_emploi_service.repository;

import com.gogo.offre_emploi_service.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Offre> findByActiveTrue();
    List<Offre> findByRecruteurId(Long recruteurId);
    List<Offre> findByActiveTrueAndDateFinAffichageBefore(LocalDateTime date);

    List<Offre>  findByEmployeurId(Long employeurId);

    @Query("SELECT o.recruteurId, COUNT(o) FROM Offre o WHERE o.recruteurId IS NOT NULL GROUP BY o.recruteurId")
    List<Object[]> countOffresByRecruteur();

    @Query("SELECT o.employeurId, COUNT(o) FROM Offre o WHERE o.employeurId IS NOT NULL GROUP BY o.employeurId")
    List<Object[]> countOffresByEmployeur();


}

