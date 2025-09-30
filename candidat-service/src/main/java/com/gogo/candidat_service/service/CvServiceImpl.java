package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.CvDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.CvNotFoundException;
import com.gogo.candidat_service.mapper.CvMapper;
import com.gogo.candidat_service.model.CV;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.repository.CVRepository;
import com.gogo.candidat_service.repository.CandidatRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CvServiceImpl implements CvService {

    private final CVRepository cvRepository;
    private final CandidatRepository candidatRepository;

    private final String uploadDir = System.getProperty("user.home") + File.separator + "uploads" + File.separator + "cv" + File.separator;

    public CvServiceImpl(CVRepository cvRepository, CandidatRepository candidatRepository) {
        this.cvRepository = cvRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public CvDTO uploadCv(Long candidatId, MultipartFile file, String titre) throws IOException, CandidatNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        file.transferTo(new File(filePath));

        CV cv = new CV();
        cv.setTitre(titre);
        cv.setFichierUrl(filePath);
        cv.setCandidat(candidat);
        cv.setDateDepot(LocalDateTime.now());

        Integer lastVersion = cvRepository.findTopByCandidatOrderByVersionDesc(candidat)
                .map(CV::getVersion)
                .orElse(0);
        cv.setVersion(lastVersion + 1);

        CV saved = cvRepository.save(cv);
        return CvMapper.toDTO(saved);
    }

    @Override
    public CvDTO replaceCv(Long cvId, MultipartFile file, String titre) throws IOException, CvNotFoundException {
        CV oldCv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException("CV non trouvé avec id " + cvId));

        File oldFile = new File(oldCv.getFichierUrl());
        if (oldFile.exists()) oldFile.delete();

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        file.transferTo(new File(filePath));

        CV newCv = new CV();
        newCv.setTitre(titre != null ? titre : oldCv.getTitre());
        newCv.setFichierUrl(filePath);
        newCv.setCandidat(oldCv.getCandidat());
        newCv.setDateDepot(LocalDateTime.now());

        Integer lastVersion = cvRepository.findTopByCandidatOrderByVersionDesc(oldCv.getCandidat())
                .map(CV::getVersion)
                .orElse(0);
        newCv.setVersion(lastVersion + 1);

        CV saved = cvRepository.save(newCv);
        return CvMapper.toDTO(saved);
    }

    @Override
    public List<CvDTO> getCvsByCandidat(Long candidatId) throws CandidatNotFoundException {
        if (!candidatRepository.existsById(candidatId)) {
            throw new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId);
        }
        return cvRepository.findByCandidatId(candidatId)
                .stream()
                .map(CvMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteCv(Long cvId) throws CvNotFoundException {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new CvNotFoundException("CV non trouvé avec id " + cvId));

        File file = new File(cv.getFichierUrl());
        if (file.exists()) file.delete();

        cvRepository.delete(cv);
    }

    @Override
    public Resource downloadCv(Long cvId) throws IOException {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new IOException("CV introuvable avec id " + cvId));

        File file = new File(cv.getFichierUrl());
        if (!file.exists()) {
            throw new IOException("Fichier non trouvé sur le disque pour le CV ID " + cvId);
        }

        return new InputStreamResource(new FileInputStream(file));
    }
    @Override
    public String getCvFilePath(Long cvId) throws IOException {
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new IOException("CV introuvable avec id " + cvId));

        return cv.getFichierUrl(); // ex: /home/username/uploads/cv/1696002356231_cv.docx
    }

}
