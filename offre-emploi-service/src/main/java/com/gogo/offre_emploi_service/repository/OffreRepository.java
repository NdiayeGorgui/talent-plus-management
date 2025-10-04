package com.gogo.offre_emploi_service.repository;

import com.gogo.offre_emploi_service.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Offre> findByActiveTrue();
    List<Offre> findByRecruteurId(Long recruteurId);
    List<Offre> findByActiveTrueAndDateFinPublicationBefore(LocalDateTime date);
}

