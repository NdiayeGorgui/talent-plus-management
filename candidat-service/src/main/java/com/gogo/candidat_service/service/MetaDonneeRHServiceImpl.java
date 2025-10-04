package com.gogo.candidat_service.service;

import com.gogo.candidat_service.dto.MetadonneeRHDTO;
import com.gogo.candidat_service.enums.Source;
import com.gogo.candidat_service.exception.CandidatNotFoundException;
import com.gogo.candidat_service.exception.MetaDonneeRHNotFoundException;
import com.gogo.candidat_service.mapper.MetadonneeRHMapper;
import com.gogo.candidat_service.enums.Disponibilite;
import com.gogo.candidat_service.model.Candidat;
import com.gogo.candidat_service.model.MetadonneeRH;
import com.gogo.candidat_service.repository.CandidatRepository;
import com.gogo.candidat_service.repository.MetaDonneeRHRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaDonneeRHServiceImpl implements MetaDonneeRHService {

    private final MetaDonneeRHRepository repository;
    private final CandidatRepository candidatRepository;

    public MetaDonneeRHServiceImpl(MetaDonneeRHRepository repository, CandidatRepository candidatRepository) {
        this.repository = repository;
        this.candidatRepository = candidatRepository;
    }

    @Override
    public MetadonneeRHDTO addMetadonneeRh(Long candidatId, MetadonneeRHDTO dto) throws CandidatNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        if (candidat.getMetadonneeRH() != null) {
            throw new IllegalStateException("Les métadonnées RH existent déjà pour ce candidat.");
        }

        MetadonneeRH entity = MetadonneeRHMapper.fromDTO(dto);
        entity.setCandidat(candidat); // Attacher le lien

        MetadonneeRH saved = repository.save(entity);

        candidat.setMetadonneeRH(saved); // MAJ relation inverse si nécessaire
        candidatRepository.save(candidat);

        return MetadonneeRHMapper.toDTO(saved);
    }

    @Override
    public MetadonneeRHDTO updateMetadonneeRh(Long candidatId, MetadonneeRHDTO dto) throws CandidatNotFoundException, MetaDonneeRHNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        MetadonneeRH existing = candidat.getMetadonneeRH();

        if (existing == null) {
            throw new MetaDonneeRHNotFoundException("Aucune métadonnée RH trouvée pour le candidat id " + candidatId);
        }

        // Mise à jour des champs un par un
        existing.setDomaineRecherche(dto.getDomaineRecherche());
        existing.setTypeContrat(dto.getTypeContrat());
        existing.setLocalisation(dto.getLocalisation());
        if (dto.getDisponibilite() != null) {
            existing.setDisponibilite(Enum.valueOf(
                    com.gogo.candidat_service.enums.Disponibilite.class,
                    dto.getDisponibilite()
            ));
        }
        existing.setPretentionsSalariales(dto.getPretentionsSalariales());
        existing.setSource(Source.valueOf(dto.getSource()));

        MetadonneeRH saved = repository.save(existing);
        return MetadonneeRHMapper.toDTO(saved);
    }

    @Override
    public MetadonneeRHDTO getMetadonneeRhByCandidatId(Long candidatId) throws CandidatNotFoundException, MetaDonneeRHNotFoundException {
        Candidat candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé avec id " + candidatId));

        MetadonneeRH meta = candidat.getMetadonneeRH();

        if (meta == null) {
            throw new MetaDonneeRHNotFoundException("Aucune métadonnée RH trouvée pour le candidat id " + candidatId);
        }

        return MetadonneeRHMapper.toDTO(meta);
    }


    @Override
    public void delete(Long id) throws MetaDonneeRHNotFoundException {
        if (!repository.existsById(id)) {
            throw new MetaDonneeRHNotFoundException("MetaDonneeRH non trouvé avec id " + id);
        }
        repository.deleteById(id);
    }
}

