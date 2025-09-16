package com.gogo.candidat_service.repository;

import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.LettreMotivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LettreMotivationRepository extends JpaRepository<LettreMotivation, Long> {
    List<LettreMotivation> findByCandidat(Candidat candidat);

    List<LettreMotivation> findByCandidatId(Long candidatId);

    Optional<LettreMotivation> findTopByCandidatOrderByVersionDesc(Candidat candidat);
}

