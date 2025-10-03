package com.gogo.candidat_service.service;


import com.gogo.candidat_service.dto.MetadonneeRHDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.MetaDonneeRHNotFoundException;

import java.util.List;

public interface MetaDonneeRHService {
    MetadonneeRHDTO addMetadonneeRh(Long candidatId, MetadonneeRHDTO dto)
            throws CandidatNotFoundException;
    MetadonneeRHDTO getMetadonneeRhByCandidatId(Long candidatId)
            throws CandidatNotFoundException, MetaDonneeRHNotFoundException;
    MetadonneeRHDTO updateMetadonneeRh(Long candidatId, MetadonneeRHDTO dto)
            throws CandidatNotFoundException, MetaDonneeRHNotFoundException;
    void delete(Long id) throws MetaDonneeRHNotFoundException;
}

