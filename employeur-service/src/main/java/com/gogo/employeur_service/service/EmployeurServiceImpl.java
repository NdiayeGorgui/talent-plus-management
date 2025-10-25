package com.gogo.employeur_service.service;

import com.gogo.employeur_service.dto.EmployeurDTO;
import com.gogo.employeur_service.exception.EmployeurNotFoundException;
import com.gogo.employeur_service.mapper.EmployeurMapper;
import com.gogo.employeur_service.model.Employeur;
import com.gogo.employeur_service.repository.EmployeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeurServiceImpl implements EmployeurService {

    private final EmployeurRepository repository;

    @Override
    public EmployeurDTO createEmployeur(EmployeurDTO dto) {
        Employeur entity = EmployeurMapper.fromDTO(dto);
        Employeur saved = repository.save(entity);
        return EmployeurMapper.toDTO(saved);
    }

    @Override
    public EmployeurDTO getEmployeurById(Long id) throws EmployeurNotFoundException {
        Employeur employeur = repository.findById(id)
                .orElseThrow(() -> new EmployeurNotFoundException("Employeur non trouvé avec id " + id));
        return EmployeurMapper.toDTO(employeur);
    }

    @Override
    public List<EmployeurDTO> getAllEmployeurs() {
        return repository.findAll()
                .stream()
                .map(EmployeurMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeurDTO updateEmployeur(Long id, EmployeurDTO dto) throws EmployeurNotFoundException {
        Employeur updated = repository.findById(id)
                .map(existing -> {
                    existing.setNom(dto.getNom());
                    existing.setTypeEntreprise(dto.getTypeEntreprise());
                    existing.setEmailContact(dto.getEmailContact());
                    existing.setTelephone(dto.getTelephone());
                    existing.setPoste(dto.getPoste());
                    existing.setAdresse(dto.getAdresse());
                    existing.setVille(dto.getVille());
                    existing.setPays(dto.getPays());
                   return repository.save(existing);
                }
                )
                .orElseThrow(() -> new RuntimeException("Employeur non trouvé avec ID: " + id));

        return EmployeurMapper.toDTO(updated);
    }

    @Override
    public void deleteEmployeur(Long id) throws EmployeurNotFoundException {
        if (!repository.existsById(id)) {
            throw new EmployeurNotFoundException("Employeur non trouvé avec id " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<EmployeurDTO> findByEmail(String email)  {
        return repository.findByEmailContact(email)
                .map(EmployeurMapper::toDTO);
    }

}

