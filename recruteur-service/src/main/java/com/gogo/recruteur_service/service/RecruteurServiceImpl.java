package com.gogo.recruteur_service.service;

import com.gogo.recruteur_service.dto.AdminDTO;
import com.gogo.recruteur_service.dto.RecruteurDTO;
import com.gogo.recruteur_service.exception.AdminNotFoundException;
import com.gogo.recruteur_service.exception.RecruteurNotFoundException;
import com.gogo.recruteur_service.mapper.AdminMapper;
import com.gogo.recruteur_service.mapper.RecruteurMapper;
import com.gogo.recruteur_service.model.Admin;
import com.gogo.recruteur_service.model.Recruteur;
import com.gogo.recruteur_service.repository.AdminRepository;
import com.gogo.recruteur_service.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecruteurServiceImpl implements RecruteurService {

    private final RecruteurRepository recruteurRepository;
    private final AdminRepository adminRepository;

    public RecruteurServiceImpl(RecruteurRepository recruteurRepository, AdminRepository adminRepository) {
        this.recruteurRepository = recruteurRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public RecruteurDTO addRecruteur(RecruteurDTO dto) {
        Recruteur recruteur = RecruteurMapper.fromDTO(dto);
        return RecruteurMapper.toDTO(recruteurRepository.save(recruteur));
    }

    @Override
    public List<RecruteurDTO> getAllRecruteurs() {
        return recruteurRepository.findAll().stream()
                .map(RecruteurMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecruteurDTO getRecruteurById(Long id) throws RecruteurNotFoundException {
        Recruteur recruteur = recruteurRepository.findById(id)
                .orElseThrow(() -> new RecruteurNotFoundException("Recruteur non trouvé avec id " + id));
        return RecruteurMapper.toDTO(recruteur);
    }

    @Override
    public RecruteurDTO updateRecruteur(Long id, RecruteurDTO dto) throws RecruteurNotFoundException {
        Recruteur updated = recruteurRepository.findById(id)
                .map(existing -> {
                    existing.setNom(dto.getNom());
                    existing.setPrenom(dto.getPrenom());
                    existing.setEmail(dto.getEmail());
                    existing.setTelephone(dto.getTelephone());
                    existing.setPoste(dto.getPoste());
                    existing.setNiveau(dto.getNiveau());
                    return recruteurRepository.save(existing);
                })
                .orElseThrow(() -> new RecruteurNotFoundException("Recruteur non trouvé avec id " + id));
        return RecruteurMapper.toDTO(updated);
    }

    @Override
    public AdminDTO updateAdmin(Long id, AdminDTO dto) throws AdminNotFoundException {
        Admin updated = adminRepository.findById(id)
                .map(existing -> {
                    existing.setNom(dto.getNom());
                    existing.setPrenom(dto.getPrenom());
                    existing.setEmail(dto.getEmail());
                    existing.setTelephone(dto.getTelephone());
                    existing.setPoste(dto.getPoste());

                    return adminRepository.save(existing);
                })
                .orElseThrow(() -> new AdminNotFoundException("Recruteur non trouvé avec id " + id));
        return AdminMapper.toDTO(updated);
    }

    @Override
    public void deleteRecruteur(Long id) throws RecruteurNotFoundException {
        if (!recruteurRepository.existsById(id)) {
            throw new RecruteurNotFoundException("Recruteur non trouvé avec id " + id);
        }
        recruteurRepository.deleteById(id);
    }

    @Override
    public Optional<RecruteurDTO> findByEmail(String email) {
        return recruteurRepository.findByEmail(email)
                .map(RecruteurMapper::toDTO);
    }

    @Override
    public Optional<AdminDTO> findByAdminEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(AdminMapper::toDTO);
    }

}

