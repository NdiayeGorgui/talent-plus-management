package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.LettreDTO;
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

    // Ajouter une lettre texte simple avec revision
    @Override
    public LettreDTO addLettre(Long candidatId, LettreDTO dto) {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        // Créer l'entité à partir du DTO
        LettreMotivation lettre = LettreMapper.fromDTO(dto, candidat);

        // Déterminer la nouvelle version
        Integer lastRevision = lettreRepository.findTopByCandidatOrderByVersionDesc(candidat)
                .map(LettreMotivation::getVersion)
                .orElse(0);
        lettre.setVersion(lastRevision + 1);

        // Sauvegarder
        LettreMotivation savedLettre = lettreRepository.save(lettre);

        // Retourner le DTO correspondant
        return LettreMapper.toDTO(savedLettre);
    }



    // Liste des lettres d’un candidat (DTO pour éviter cycle)
    @Override
    public List<LettreDTO> getLettresByCandidat(Long candidatId) {
        return lettreRepository.findByCandidatId(candidatId).stream()
                .map(LettreMapper::toDTO)
                .toList();
    }

    // Supprimer une lettre et le fichier
    @Override
    public void deleteLettre(Long lettreId) {
        LettreMotivation lettre = lettreRepository.findById(lettreId)
                .orElseThrow(() -> new RuntimeException("Lettre non trouvée"));

        if (lettre.getFichierUrl() != null) {
            File file = new File(lettre.getFichierUrl());
            if (file.exists()) file.delete();
        }

        lettreRepository.deleteById(lettreId);
    }


    @Override
    public LettreDTO uploadLettre(Long candidatId, MultipartFile file, String titre) throws IOException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Nom unique du fichier
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        LettreMotivation lettre = new LettreMotivation();
        lettre.setTitre(titre);
        lettre.setFichierUrl(filePath);
        lettre.setCandidat(candidat);

        // Nouvelle version
        Integer lastRevision = lettreRepository.findTopByCandidatOrderByVersionDesc(candidat)
                .map(LettreMotivation::getVersion)
                .orElse(0);
        lettre.setVersion(lastRevision + 1);

        LettreMotivation savedLettre = lettreRepository.save(lettre);
        return LettreMapper.toDTO(savedLettre);
    }

    @Override
    public LettreDTO replaceLettre(Long lettreId, MultipartFile file, String titre) throws IOException {
        LettreMotivation oldLettre = lettreRepository.findById(lettreId)
                .orElseThrow(() -> new RuntimeException("Lettre non trouvée"));

        // Supprimer le fichier physique existant
        if (oldLettre.getFichierUrl() != null) {
            File oldFile = new File(oldLettre.getFichierUrl());
            if (oldFile.exists()) oldFile.delete();
        }

        // Enregistrer le nouveau fichier
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

