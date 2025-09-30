package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CvDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CvNotFoundException;
import com.gogo.candidat_service.model.CV;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CvService {
    CvDTO uploadCv(Long candidatId, MultipartFile file, String titre) throws IOException, CandidatNotFoundException;
    CvDTO replaceCv(Long cvId, MultipartFile file, String titre) throws IOException, CvNotFoundException;
    List<CvDTO> getCvsByCandidat(Long candidatId) throws CandidatNotFoundException;
    void deleteCv(Long cvId) throws CvNotFoundException;
    Resource downloadCv(Long cvId) throws IOException;
}
