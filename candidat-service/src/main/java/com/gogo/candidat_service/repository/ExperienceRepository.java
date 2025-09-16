package com.gogo.candidat_service.repository;

import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCandidat(Candidat candidat);

    List<Experience> findByCandidatId(Long candidatId);
}

