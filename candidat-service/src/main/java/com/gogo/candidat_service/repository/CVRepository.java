package com.gogo.candidat_service.repository;

import com.gogo.candidat_service.model.CV;
import com.gogo.candidat_service.model.Candidat;
import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {
    List<CV> findByCandidat(Candidat candidat);

    List<CV> findByCandidatId(Long candidatId);


    Optional<CV> findTopByCandidatOrderByVersionDesc(Candidat candidat);
}

