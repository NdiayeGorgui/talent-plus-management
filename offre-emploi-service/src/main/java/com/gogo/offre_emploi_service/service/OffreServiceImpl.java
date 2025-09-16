package com.gogo.offre_emploi_service.service;

import com.gogo.offre_emploi_service.dto.OffreDTO;
import com.gogo.offre_emploi_service.mapper.OffreMapper;
import com.gogo.offre_emploi_service.model.Offre;
import com.gogo.offre_emploi_service.repository.OffreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OffreServiceImpl implements OffreService {

    private final OffreRepository offreRepository;

    public OffreServiceImpl(OffreRepository offreRepository) {
        this.offreRepository = offreRepository;
    }

    @Override
    public OffreDTO createOffre(OffreDTO dto) {
        Offre offre = OffreMapper.fromDTO(dto);
        return OffreMapper.toDTO(offreRepository.save(offre));
    }

    @Override
    public OffreDTO updateOffre(Long id, OffreDTO dto) {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée avec id " + id));
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setActive(dto.isActive());
        return OffreMapper.toDTO(offreRepository.save(offre));
    }

    @Override
    public void closeOffre(Long id) {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée avec id " + id));
        offre.setActive(false);
        offreRepository.save(offre);
    }

    @Override
    public List<OffreDTO> getAllOffres() {
        return offreRepository.findAll().stream()
                .map(OffreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OffreDTO getOffreById(Long id) {
        return offreRepository.findById(id)
                .map(OffreMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée avec id " + id));
    }
}
