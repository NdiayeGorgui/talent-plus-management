package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.CandidatureParMoisDTO;
import com.gogo.candidat_service.dto.PostulerRequest;
import com.gogo.candidat_service.model.Candidat;
import com.netflix.appinfo.ApplicationInfoManager;

import java.util.List;

public interface CandidatService {
    CandidatDTO saveCandidat(Candidat candidat);
    CandidatDTO updateCandidat(Long id, Candidat candidat);
    void deleteCandidat(Long id);
    CandidatDTO getCandidatById(Long id);
    CandidatDTO getCandidatByEmail(String email);
    List<CandidatDTO> getAllCandidats();
    List<CandidatDTO> searchCandidats(String keyword);
    Candidat postuler(PostulerRequest request);

    CandidatDTO findById(Long id);
    // List<CandidatureParMoisDTO> getCandidaturesParMois();
}

