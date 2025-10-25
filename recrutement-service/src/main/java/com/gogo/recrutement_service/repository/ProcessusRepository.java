package com.gogo.recrutement_service.repository;

import com.gogo.recrutement_service.enums.TypeCandidature;
import com.gogo.recrutement_service.model.Processus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessusRepository extends JpaRepository<Processus, Long> {
    List<Processus> findByCandidatId(Long candidatId);
    List<Processus> findByOffreId(Long offreId);
    List<Processus> findByTypeCandidature(TypeCandidature typeCandidature);
    List<Processus> findByOffreIdIn(List<Long> offreIds);
    boolean existsByCandidatIdAndOffreId(Long candidatId, Long offreId);
    List<Processus> findByOffreIdIsNull();

}
