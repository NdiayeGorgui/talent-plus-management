package com.gogo.recrutement_service.repository;

import com.gogo.recrutement_service.model.Historique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {
    List<Historique> findByProcessusId(Long processusId);
}
