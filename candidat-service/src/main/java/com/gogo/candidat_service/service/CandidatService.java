package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CandidatDTO;
import com.gogo.candidat_service.dto.CandidatResponseDTO;
import com.gogo.candidat_service.dto.PostulerRequest;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.model.Candidat;

import java.util.List;
import java.util.Optional;

public interface CandidatService {
    CandidatDTO saveCandidat(Candidat candidat);
    CandidatDTO updateCandidat(Long id, Candidat candidat) throws CandidatNotFoundException;
    void deleteCandidat(Long id) throws CandidatNotFoundException;
    CandidatResponseDTO getCandidatById(Long id) throws CandidatNotFoundException;
    CandidatResponseDTO  getCandidatByEmail(String email) throws CandidatNotFoundException;
    List<CandidatResponseDTO > getAllCandidats();
    List<CandidatResponseDTO > searchCandidats(String keyword);
    Candidat postuler(PostulerRequest request);

    CandidatResponseDTO  findById(Long id) throws CandidatNotFoundException;

    Optional<CandidatResponseDTO> findByEmail(String email);
    // List<CandidatureParMoisDTO> getCandidaturesParMois();
    List<CandidatDTO> findByIds(List<Long> ids);

}

