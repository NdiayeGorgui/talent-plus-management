package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.LettreDTO;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.LettreMotivationNotFoundException;
import com.gogo.candidat_service.mapper.LettreMapper;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.LettreMotivation;
import com.gogo.candidat_service.repository.CandidatRepository;
import com.gogo.candidat_service.repository.LettreMotivationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LettreMotivationServiceImpl implements LettreMotivationService {

    private final LettreMotivationRepository lettreRepository;
    private final CandidatRepository candidatRepository;
    private final String uploadDir = System.getProperty("user.home")
            + File.separator + "uploads"
            + File.separator + "lettres"
            + File.separator;

    public LettreMotivationServiceImpl(LettreMotivationRepository lettreRepository, CandidatRepository candidatRepository) {
        this.lettreRepository = lettreRepository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public LettreDTO addLettre(Long candidatId, LettreDTO dto) throws CandidatNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        LettreMotivation lettre = LettreMapper.fromDTO(dto, candidat);

        Integer lastRevision = lettreRepository.findTopByCandidatOrderByVersionDesc(candidat)
                .map(LettreMotivation::getVersion)
                .orElse(0);
        lettre.setVersion(lastRevision + 1);

        LettreMotivation savedLettre = lettreRepository.save(lettre);
        return LettreMapper.toDTO(savedLettre);
    }

    @Override
    public List<LettreDTO> getLettresByCandidat(Long candidatId) throws CandidatNotFoundException {
        if (!candidatRepository.existsById(candidatId)) {
            throw new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId);
        }
        return lettreRepository.findByCandidatId(candidatId).stream()
                .map(LettreMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteLettre(Long lettreId) throws LettreMotivationNotFoundException {
        LettreMotivation lettre = lettreRepository.findById(lettreId)
                .orElseThrow(() -> new LettreMotivationNotFoundException("Lettre non trouvée avec id " + lettreId));

        if (lettre.getFichierUrl() != null) {
            File file = new File(lettre.getFichierUrl());
            if (file.exists()) file.delete();
        }

        lettreRepository.deleteById(lettreId);
    }

    @Override
    public LettreDTO uploadLettre(Long candidatId, MultipartFile file, String titre) throws IOException, CandidatNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        LettreMotivation lettre = new LettreMotivation();
        lettre.setTitre(titre);
        lettre.setFichierUrl(filePath);
        lettre.setCandidat(candidat);

        Integer lastRevision = lettreRepository.findTopByCandidatOrderByVersionDesc(candidat)
                .map(LettreMotivation::getVersion)
                .orElse(0);
        lettre.setVersion(lastRevision + 1);

        LettreMotivation savedLettre = lettreRepository.save(lettre);
        return LettreMapper.toDTO(savedLettre);
    }

    @Override
    public LettreDTO replaceLettre(Long lettreId, MultipartFile file, String titre) throws IOException, LettreMotivationNotFoundException {
        LettreMotivation oldLettre = lettreRepository.findById(lettreId)
                .orElseThrow(() -> new LettreMotivationNotFoundException("Lettre non trouvée avec id " + lettreId));

        if (oldLettre.getFichierUrl() != null) {
            File oldFile = new File(oldLettre.getFichierUrl());
            if (oldFile.exists()) oldFile.delete();
        }

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        oldLettre.setTitre(titre != null ? titre : oldLettre.getTitre());
        oldLettre.setFichierUrl(filePath);
        oldLettre.setDateDepot(LocalDateTime.now());
        oldLettre.setVersion(oldLettre.getVersion() + 1);

        LettreMotivation savedLettre = lettreRepository.save(oldLettre);
        return LettreMapper.toDTO(savedLettre);
    }
}
